package com.torchslot.mixin.client;

import com.torchslot.LightSlotContainer;
import com.torchslot.client.LightSlotUi;
import com.torchslot.mixin.SlotAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * In the creative inventory tab the light slot is always shown, mirroring the offhand slot on
 * the other side of the armor (vanilla would otherwise lay it out on top of the hotbar).
 */
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {
    @Inject(method = "selectTab", at = @At("TAIL"))
    private void torchSlot$placeLightSlot(CreativeModeTab tab, CallbackInfo ci) {
        CreativeModeInventoryScreen self = (CreativeModeInventoryScreen) (Object) this;
        if (!self.isInventoryOpen()) {
            return;
        }
        for (Slot slot : self.getMenu().slots) {
            if (slot.container instanceof LightSlotContainer) {
                ((SlotAccessor) slot).torchSlot$setX(LightSlotUi.CREATIVE_X);
                ((SlotAccessor) slot).torchSlot$setY(LightSlotUi.CREATIVE_Y);
            }
        }
    }

    @Inject(method = "extractBackground", at = @At("TAIL"))
    private void torchSlot$drawSlotFrame(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        CreativeModeInventoryScreen self = (CreativeModeInventoryScreen) (Object) this;
        if (self.isInventoryOpen()) {
            LightSlotUi.drawCreativeSlotFrame(self, graphics);
        }
    }
}
