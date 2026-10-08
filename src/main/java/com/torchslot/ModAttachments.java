package com.torchslot;

import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TorchSlot.MOD_ID);

    /**
     * The item in a player's light slot. Saved with the player, kept through death only when
     * keepInventory is on (otherwise {@link ServerEvents} drops it first), and synced to every
     * client that can see the player so their light shows up for everyone.
     */
    public static final Supplier<AttachmentType<ItemStack>> LIGHT_ITEM = ATTACHMENT_TYPES.register("light_item",
            () -> AttachmentType.builder(() -> ItemStack.EMPTY)
                    .serialize(ItemStack.OPTIONAL_CODEC.fieldOf("item"), stack -> !stack.isEmpty())
                    .copyOnDeath()
                    .sync(ItemStack.OPTIONAL_STREAM_CODEC)
                    .build());

    private ModAttachments() {}
}
