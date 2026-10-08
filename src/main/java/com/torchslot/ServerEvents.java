package com.torchslot;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = TorchSlot.MOD_ID)
public class ServerEvents {

    /** The light slot drops on death like the rest of the inventory, unless keepInventory is on. */
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        ServerLevel level = player.level();
        if (level.getGameRules().get(GameRules.KEEP_INVENTORY)) {
            return;
        }
        ItemStack stack = player.getData(ModAttachments.LIGHT_ITEM);
        if (stack.isEmpty()) {
            return;
        }
        player.setData(ModAttachments.LIGHT_ITEM, ItemStack.EMPTY);
        player.syncData(ModAttachments.LIGHT_ITEM);
        ItemEntity drop = new ItemEntity(level, player.getX(), player.getEyeY() - 0.3, player.getZ(), stack);
        drop.setPickUpDelay(40);
        event.getDrops().add(drop);
    }
}
