package com.torchslot;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * Main entrypoint for the Torch Slot mod.
 *
 * Adds one extra slot to the player inventory (next to the chestplate) that holds a light
 * source. A player with a light in the slot lights up the world around them; the lighting
 * itself is purely client-side (see {@code client.DynamicLights}).
 */
@Mod(TorchSlot.MOD_ID)
public class TorchSlot {
    public static final String MOD_ID = "torch_slot";

    public TorchSlot(IEventBus modEventBus, ModContainer modContainer) {
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
    }
}
