package com.torchslot.mixin.client;

import com.torchslot.client.LightSlotUi;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Clicking the light slot's pop-out panel must not count as clicking outside (which throws the carried item). */
@Mixin(AbstractRecipeBookScreen.class)
public abstract class AbstractRecipeBookScreenMixin {
    @Inject(method = "hasClickedOutside", at = @At("HEAD"), cancellable = true)
    private void torchSlot$panelIsInside(double mx, double my, int xo, int yo, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof InventoryScreen screen && LightSlotUi.isOverPanel(screen, mx, my)) {
            cir.setReturnValue(false);
        }
    }
}
