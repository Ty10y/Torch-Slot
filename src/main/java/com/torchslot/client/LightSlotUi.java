package com.torchslot.client;

import com.torchslot.LightSlot;
import com.torchslot.LightSlotContainer;
import com.torchslot.mixin.client.AbstractContainerScreenAccessor;
import com.torchslot.mixin.client.AbstractRecipeBookScreenAccessor;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;
import org.jspecify.annotations.Nullable;

/**
 * The light slot's place in the inventory screens.
 *
 * Survival inventory: a small "+" on the left edge beside the chestplate slot pops out a tab
 * holding the slot, tucked behind the GUI's left edge. The tab hides while the recipe book is
 * open, since the book sits in the same spot.
 * Creative inventory tab: the slot is always shown, mirroring the offhand slot.
 */
public final class LightSlotUi {
    public static final int CREATIVE_X = 127;
    public static final int CREATIVE_Y = 20;

    /** The "+" button, relative to the GUI: on the left border, level with the chestplate slot. */
    static final int BUTTON_X = 0;
    static final int BUTTON_Y = LightSlot.Y + 4;
    static final int BUTTON_SIZE = 7;

    /** The pop-out tab, relative to the GUI. Its right part is hidden under the inventory texture. */
    private static final int PANEL_X0 = LightSlot.X - 8;
    private static final int PANEL_X1 = 4;
    private static final int PANEL_Y0 = LightSlot.Y - 8;
    private static final int PANEL_Y1 = LightSlot.Y + 16 + 8;

    private static final Identifier SLOT_SPRITE = Identifier.withDefaultNamespace("container/slot");

    /** Whether the tab is popped out. Remembered for the rest of the session. */
    private static boolean open;
    private static @Nullable LightSlotToggle toggle;

    private LightSlotUi() {}

    public static boolean isOpen() {
        return open;
    }

    static void toggle() {
        open = !open;
    }

    /** Backs {@link LightSlot#clientVisible}. */
    public static boolean isSlotVisible() {
        Screen screen = Minecraft.getInstance().gui.screen();
        if (screen instanceof CreativeModeInventoryScreen) {
            return true;
        }
        if (screen instanceof InventoryScreen inventoryScreen) {
            return isPanelShown(inventoryScreen);
        }
        return open;
    }

    static boolean isRecipeBookOpen(InventoryScreen screen) {
        return ((AbstractRecipeBookScreenAccessor) screen).torchSlot$getRecipeBookComponent().isVisible();
    }

    private static boolean isPanelShown(InventoryScreen screen) {
        return open && !isRecipeBookOpen(screen);
    }

    public static boolean isOverPanel(InventoryScreen screen, double mouseX, double mouseY) {
        if (!isPanelShown(screen)) {
            return false;
        }
        AbstractContainerScreenAccessor gui = (AbstractContainerScreenAccessor) screen;
        double x = mouseX - gui.torchSlot$getLeftPos();
        double y = mouseY - gui.torchSlot$getTopPos();
        return x >= PANEL_X0 && x < 0 && y >= PANEL_Y0 && y < PANEL_Y1;
    }

    /** Draws the tab in the vanilla GUI style: black outline, white top-left bevel, grey bottom shadow. */
    public static void drawPanel(InventoryScreen screen, GuiGraphicsExtractor graphics) {
        if (!isPanelShown(screen)) {
            return;
        }
        AbstractContainerScreenAccessor gui = (AbstractContainerScreenAccessor) screen;
        int left = gui.torchSlot$getLeftPos();
        int top = gui.torchSlot$getTopPos();
        int x0 = left + PANEL_X0;
        int x1 = left + PANEL_X1;
        int y0 = top + PANEL_Y0;
        int y1 = top + PANEL_Y1;
        graphics.fill(x0 + 1, y0, x1, y0 + 1, 0xFF000000);
        graphics.fill(x0, y0 + 1, x0 + 1, y1 - 1, 0xFF000000);
        graphics.fill(x0 + 1, y1 - 1, x1, y1, 0xFF000000);
        graphics.fill(x0 + 1, y0 + 1, x1, y1 - 1, 0xFFC6C6C6);
        graphics.fill(x0 + 1, y0 + 1, x1, y0 + 3, 0xFFFFFFFF);
        graphics.fill(x0 + 1, y0 + 1, x0 + 3, y1 - 3, 0xFFFFFFFF);
        graphics.fill(x0 + 3, y1 - 3, x1, y1 - 1, 0xFF555555);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_SPRITE, left + LightSlot.X - 1, top + LightSlot.Y - 1, 18, 18);
    }

    public static void drawCreativeSlotFrame(CreativeModeInventoryScreen screen, GuiGraphicsExtractor graphics) {
        AbstractContainerScreenAccessor gui = (AbstractContainerScreenAccessor) screen;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_SPRITE,
                gui.torchSlot$getLeftPos() + CREATIVE_X - 1, gui.torchSlot$getTopPos() + CREATIVE_Y - 1, 18, 18);
    }

    // ---- Screen event hooks (wired up in ClientSetup) ----

    static void onScreenInit(Screen screen, java.util.function.Consumer<LightSlotToggle> addWidget) {
        if (screen instanceof InventoryScreen inventoryScreen) {
            toggle = new LightSlotToggle(inventoryScreen);
            addWidget.accept(toggle);
        }
    }

    /** Keeps the "+" glued to the GUI, which moves when the recipe book opens or closes. */
    static void onScreenRender(Screen screen) {
        if (toggle != null && toggle.screen == screen) {
            toggle.update();
        }
    }

    /** A release right after pressing the "+" must not count as a click on the inventory (it would drop the carried item). */
    static boolean consumeToggleRelease(Screen screen) {
        return toggle != null && toggle.screen == screen && toggle.consumeRelease();
    }

    /** Names the empty slot on hover, so players know what it is for. */
    static void drawEmptySlotTooltip(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (!(screen instanceof AbstractContainerScreen<?> containerScreen)) {
            return;
        }
        Slot hovered = ((AbstractContainerScreenAccessor) containerScreen).torchSlot$getHoveredSlot();
        if (hovered == null || hovered.hasItem() || !(hovered.container instanceof LightSlotContainer)
                || !containerScreen.getMenu().getCarried().isEmpty()) {
            return;
        }
        List<Component> lines = List.of(
                Component.translatable("torch_slot.slot.light"),
                Component.translatable("torch_slot.slot.light.hint").withStyle(ChatFormatting.GRAY));
        graphics.setTooltipForNextFrame(Minecraft.getInstance().font, lines, Optional.empty(), mouseX, mouseY);
    }
}
