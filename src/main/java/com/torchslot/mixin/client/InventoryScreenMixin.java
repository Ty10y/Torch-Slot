package com.torchslot.mixin.client;

import com.torchslot.client.LightSlotUi;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Draws the light slot's pop-out panel just before the inventory texture, so it tucks in behind the GUI's edge. */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {
    @Inject(
            method = "extractBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V",
                    shift = At.Shift.AFTER))
    private void torchSlot$drawPanel(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        LightSlotUi.drawPanel((InventoryScreen) (Object) this, graphics);
    }
}
