package com.torchslot.mixin.client;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {
    @Accessor("leftPos")
    int torchSlot$getLeftPos();

    @Accessor("topPos")
    int torchSlot$getTopPos();

    @Accessor("hoveredSlot")
    @Nullable Slot torchSlot$getHoveredSlot();
}
