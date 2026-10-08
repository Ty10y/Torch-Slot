package com.torchslot.mixin;

import com.torchslot.LightSlot;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/** Creative-mode slot edits are only accepted for slots 1-45; let them reach the light slot too. */
@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    @ModifyConstant(method = "handleSetCreativeModeSlot", constant = @Constant(intValue = 45))
    private int torchSlot$allowLightSlot(int lastSlot) {
        return LightSlot.MENU_INDEX;
    }
}
