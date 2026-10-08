package com.torchslot.mixin.client;

import com.torchslot.client.DynamicLights;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Entities (including the wearer and their first-person hand) are lit every frame from their
 * exact position, so they brighten and dim smoothly with no chunk rebuild involved.
 */
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    @Inject(method = "getPackedLightCoords", at = @At("RETURN"), cancellable = true)
    private void torchSlot$dynamicLight(Entity entity, float partialTickTime, CallbackInfoReturnable<Integer> cir) {
        if (DynamicLights.isActive()) {
            Vec3 probe = entity.getLightProbePosition(partialTickTime);
            cir.setReturnValue(DynamicLights.lightEntity(cir.getReturnValueI(), probe, partialTickTime));
        }
    }
}
