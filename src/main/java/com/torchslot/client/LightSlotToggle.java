package com.torchslot.client;

import com.torchslot.mixin.client.AbstractContainerScreenAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

/** The little "+" (or "-" while open) that pops the light slot out of the survival inventory. */
class LightSlotToggle extends AbstractButton {
    final InventoryScreen screen;
    private boolean shownOpen;

    LightSlotToggle(InventoryScreen screen) {
        super(0, 0, LightSlotUi.BUTTON_SIZE, LightSlotUi.BUTTON_SIZE, Component.translatable("torch_slot.button.show"));
        this.screen = screen;
        this.update();
        this.refreshLabel();
    }

    void update() {
        AbstractContainerScreenAccessor gui = (AbstractContainerScreenAccessor) this.screen;
        this.setPosition(gui.torchSlot$getLeftPos() + LightSlotUi.BUTTON_X, gui.torchSlot$getTopPos() + LightSlotUi.BUTTON_Y);
        if (this.shownOpen != LightSlotUi.isOpen()) {
            this.refreshLabel();
        }
    }

    private void refreshLabel() {
        this.shownOpen = LightSlotUi.isOpen();
        Component label = Component.translatable(this.shownOpen ? "torch_slot.button.hide" : "torch_slot.button.show");
        this.setMessage(label);
        this.setTooltip(Tooltip.create(label));
    }

    @Override
    public void onPress(InputWithModifiers input) {
        LightSlotUi.toggle(this.screen, input instanceof MouseButtonEvent);
        this.refreshLabel();
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int x = this.getX();
        int y = this.getY();
        int size = this.getWidth();
        graphics.fill(x, y, x + size, y + size, 0xFF373737);
        graphics.fill(x + 1, y + 1, x + size - 1, y + size - 1, this.isHoveredOrFocused() ? 0xFFB0B0B0 : 0xFF8B8B8B);
        int mid = size / 2;
        graphics.fill(x + 2, y + mid, x + size - 2, y + mid + 1, 0xFFFFFFFF);
        if (!this.shownOpen) {
            graphics.fill(x + mid, y + 2, x + mid + 1, y + size - 2, 0xFFFFFFFF);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }
}
