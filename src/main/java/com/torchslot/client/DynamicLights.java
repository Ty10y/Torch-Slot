package com.torchslot.client;

import com.torchslot.LightSources;
import com.torchslot.ModAttachments;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Client-side moving lights for players wearing a light source.
 *
 * Light is computed from the straight-line distance to each wearer ({@code level - distance},
 * in fractional light levels), so brightness falls off continuously instead of in whole-block
 * steps. Entities read it every frame at their exact (interpolated) position. Terrain is baked
 * into chunk meshes, so whenever a light moves a little or fades, the sections it touches are
 * queued for an (async) rebuild.
 */
public final class DynamicLights {
    /** How far a light must move before the terrain around it is re-meshed. */
    private static final double REBUILD_DISTANCE_SQR = 0.1 * 0.1;
    /** Light levels gained or lost per tick when a light is put in or taken out (~0.5 s for a lantern). */
    private static final float FADE_PER_TICK = 1.5F;
    /** Extra reach when picking sections to rebuild: smooth lighting samples neighbouring blocks. */
    private static final double SECTION_MARGIN = 1.5;
    private static final Source[] NO_SOURCES = new Source[0];

    /** One light, as seen by render threads (chunk meshing runs on worker threads). */
    private record Source(double x, double y, double z, float level, double prevX, double prevY, double prevZ, float prevLevel) {}

    private static volatile Source[] sources = NO_SOURCES;

    /** Main-thread bookkeeping per lit player, keyed by entity id. */
    private static final class Tracked {
        double x, y, z, prevX, prevY, prevZ;
        float level, prevLevel, target;
        /** Position and level the surrounding chunk meshes were last rebuilt for. */
        double builtX, builtY, builtZ;
        float builtLevel;
        boolean seen;
    }

    private static final Int2ObjectMap<Tracked> TRACKED = new Int2ObjectOpenHashMap<>();
    private static @Nullable ClientLevel trackedLevel;

    private DynamicLights() {}

    public static boolean isActive() {
        return sources.length > 0;
    }

    public static void clear() {
        TRACKED.clear();
        sources = NO_SOURCES;
        trackedLevel = null;
    }

    public static void tick() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level != trackedLevel) {
            // New world or dimension: the old meshes are gone anyway.
            clear();
            trackedLevel = level;
        }
        if (level == null) {
            return;
        }

        for (Tracked tracked : TRACKED.values()) {
            tracked.seen = false;
        }

        for (Player player : level.players()) {
            int target = player.isSpectator() ? 0 : LightSources.lightLevel(player.getData(ModAttachments.LIGHT_ITEM));
            Tracked tracked = TRACKED.get(player.getId());
            if (tracked == null) {
                if (target == 0) {
                    continue;
                }
                tracked = new Tracked();
                moveTo(tracked, player);
                tracked.prevX = tracked.builtX = tracked.x;
                tracked.prevY = tracked.builtY = tracked.y;
                tracked.prevZ = tracked.builtZ = tracked.z;
                TRACKED.put(player.getId(), tracked);
            } else {
                tracked.prevX = tracked.x;
                tracked.prevY = tracked.y;
                tracked.prevZ = tracked.z;
                moveTo(tracked, player);
            }
            tracked.seen = true;
            tracked.target = target;
        }

        Iterator<Tracked> iterator = TRACKED.values().iterator();
        while (iterator.hasNext()) {
            Tracked tracked = iterator.next();
            if (!tracked.seen) {
                // Player left view or logged off: fade out where they were last seen.
                tracked.target = 0;
                tracked.prevX = tracked.x;
                tracked.prevY = tracked.y;
                tracked.prevZ = tracked.z;
            }
            tracked.prevLevel = tracked.level;
            tracked.level = approach(tracked.level, tracked.target, FADE_PER_TICK);

            double dx = tracked.x - tracked.builtX;
            double dy = tracked.y - tracked.builtY;
            double dz = tracked.z - tracked.builtZ;
            if (dx * dx + dy * dy + dz * dz >= REBUILD_DISTANCE_SQR || tracked.level != tracked.builtLevel) {
                markSectionsDirty(minecraft, tracked.builtX, tracked.builtY, tracked.builtZ, tracked.builtLevel);
                markSectionsDirty(minecraft, tracked.x, tracked.y, tracked.z, tracked.level);
                tracked.builtX = tracked.x;
                tracked.builtY = tracked.y;
                tracked.builtZ = tracked.z;
                tracked.builtLevel = tracked.level;
            }

            if (tracked.level <= 0.0F && tracked.target <= 0.0F) {
                iterator.remove();
            }
        }

        Source[] snapshot = new Source[TRACKED.size()];
        int i = 0;
        for (Tracked t : TRACKED.values()) {
            snapshot[i++] = new Source(t.x, t.y, t.z, t.level, t.prevX, t.prevY, t.prevZ, t.prevLevel);
        }
        sources = snapshot;
    }

    /** The light hangs at about hip height. */
    private static void moveTo(Tracked tracked, Player player) {
        tracked.x = player.getX();
        tracked.y = player.getY() + player.getBbHeight() * 0.55;
        tracked.z = player.getZ();
    }

    private static float approach(float value, float target, float step) {
        return value < target ? Math.min(value + step, target) : Math.max(value - step, target);
    }

    private static void markSectionsDirty(Minecraft minecraft, double x, double y, double z, float level) {
        if (level <= 0.0F) {
            return;
        }
        double radius = level + SECTION_MARGIN;
        double radiusSqr = radius * radius;
        int minX = SectionPos.blockToSectionCoord(Mth.floor(x - radius));
        int maxX = SectionPos.blockToSectionCoord(Mth.floor(x + radius));
        int minY = SectionPos.blockToSectionCoord(Mth.floor(y - radius));
        int maxY = SectionPos.blockToSectionCoord(Mth.floor(y + radius));
        int minZ = SectionPos.blockToSectionCoord(Mth.floor(z - radius));
        int maxZ = SectionPos.blockToSectionCoord(Mth.floor(z + radius));
        for (int sx = minX; sx <= maxX; sx++) {
            double ex = distanceToSpan(x, sx);
            for (int sy = minY; sy <= maxY; sy++) {
                double ey = distanceToSpan(y, sy);
                for (int sz = minZ; sz <= maxZ; sz++) {
                    double ez = distanceToSpan(z, sz);
                    if (ex * ex + ey * ey + ez * ez <= radiusSqr) {
                        minecraft.levelExtractor.setSectionDirty(sx, sy, sz);
                    }
                }
            }
        }
    }

    /** Distance along one axis from {@code v} to the 16-block span of section {@code s}. */
    private static double distanceToSpan(double v, int s) {
        double min = SectionPos.sectionToBlockCoord(s);
        double max = min + 16.0;
        return v < min ? min - v : v > max ? v - max : 0.0;
    }

    /** Raises the block-light part of packed light coords for a block, from the latest tick positions. */
    public static int lightBlock(int lightCoords, BlockPos pos) {
        double cx = pos.getX() + 0.5;
        double cy = pos.getY() + 0.5;
        double cz = pos.getZ() + 0.5;
        float best = 0.0F;
        for (Source source : sources) {
            float light = falloff(source.level, cx - source.x, cy - source.y, cz - source.z);
            if (light > best) {
                best = light;
            }
        }
        return withBlockLight(lightCoords, best);
    }

    /** Raises the block-light part of an entity's packed light coords, interpolating lights to this frame. */
    public static int lightEntity(int lightCoords, Vec3 probe, float partialTick) {
        float best = 0.0F;
        for (Source source : sources) {
            float level = Mth.lerp(partialTick, source.prevLevel, source.level);
            float light = falloff(level,
                    probe.x - Mth.lerp(partialTick, source.prevX, source.x),
                    probe.y - Mth.lerp(partialTick, source.prevY, source.y),
                    probe.z - Mth.lerp(partialTick, source.prevZ, source.z));
            if (light > best) {
                best = light;
            }
        }
        return withBlockLight(lightCoords, best);
    }

    private static float falloff(float level, double dx, double dy, double dz) {
        double distSqr = dx * dx + dy * dy + dz * dz;
        if (distSqr >= level * level) {
            return 0.0F;
        }
        return level - (float) Math.sqrt(distSqr);
    }

    /**
     * Packed light keeps block light in the low byte as {@code level * 16} (0-240). Writing a
     * fractional level there is what makes the falloff smooth: the lightmap is sampled between
     * its texels.
     */
    private static int withBlockLight(int lightCoords, float level) {
        int dynamic = Math.min(240, (int) (level * 16.0F));
        int current = lightCoords & 0xFFFF;
        return dynamic > current ? (lightCoords & 0xFFFF0000) | dynamic : lightCoords;
    }
}
