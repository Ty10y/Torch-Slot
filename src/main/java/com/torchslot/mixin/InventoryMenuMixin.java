package com.torchslot.mixin;

import com.torchslot.LightSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Appends the light slot to the player inventory menu, after the offhand slot, so it becomes
 * slot {@link LightSlot#MENU_INDEX}. Vanilla's quickMoveStack sends anything shift-clicked out
 * of an unknown slot back into the main inventory, which is what we want.
 */
@Mixin(InventoryMenu.class)
public abstract class InventoryMenuMixin extends AbstractContainerMenu {
    protected InventoryMenuMixin(MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void torchSlot$addLightSlot(Inventory inventory, boolean active, Player owner, CallbackInfo ci) {
        this.addSlot(new LightSlot(owner, LightSlot.X, LightSlot.Y));
    }
}
