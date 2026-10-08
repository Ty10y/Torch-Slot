package com.torchslot.mixin.client;

import com.torchslot.client.DynamicLights;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Every light lookup for rendering blocks, block entities, fluids and particles funnels through
 * here. Called from chunk-meshing worker threads too, which DynamicLights allows for.
 */
@Mixin(LightCoordsUtil.class)
public abstract class LightCoordsUtilMixin {
    @Inject(
            method = "getLightCoords(Lnet/minecraft/util/LightCoordsUtil$BrightnessGetter;Lnet/minecraft/world/level/BlockAndLightGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I",
            at = @At("RETURN"),
            cancellable = true)
    private static void torchSlot$dynamicLight(
            LightCoordsUtil.BrightnessGetter brightnessGetter, BlockAndLightGetter level, BlockState state, BlockPos pos,
            CallbackInfoReturnable<Integer> cir) {
        if (DynamicLights.isActive()) {
            cir.setReturnValue(DynamicLights.lightBlock(cir.getReturnValueI(), pos));
        }
    }
}
