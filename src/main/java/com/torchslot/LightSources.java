package com.torchslot;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EmptyBlockGetter;

/** Decides which items count as light sources, and how bright they are. */
public final class LightSources {
    private LightSources() {}

    /** Light level (0-15) the item gives off when worn; 0 means it is not a light source. */
    public static int lightLevel(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        if (stack.is(Items.LAVA_BUCKET)) {
            return 15;
        }
        if (stack.getItem() instanceof BlockItem blockItem) {
            return blockItem.getBlock().defaultBlockState().getLightEmission(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
        }
        return 0;
    }

    public static boolean isLightSource(ItemStack stack) {
        return lightLevel(stack) > 0;
    }
}
