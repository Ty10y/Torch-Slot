package com.torchslot;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** A one-slot {@link Container} view over a player's {@link ModAttachments#LIGHT_ITEM}. */
public class LightSlotContainer implements Container {
    private final Player player;

    public LightSlotContainer(Player player) {
        this.player = player;
    }

    private ItemStack get() {
        return this.player.getData(ModAttachments.LIGHT_ITEM);
    }

    private void put(ItemStack stack) {
        this.player.setData(ModAttachments.LIGHT_ITEM, stack);
        this.setChanged();
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.get().isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.get();
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack stack = this.get();
        if (stack.isEmpty() || count <= 0) {
            return ItemStack.EMPTY;
        }
        ItemStack removed = stack.split(count);
        if (stack.isEmpty()) {
            this.put(ItemStack.EMPTY);
        } else {
            this.setChanged();
        }
        return removed;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = this.get();
        this.put(ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.put(stack);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return LightSources.isLightSource(stack);
    }

    /** Any change is pushed to the owner and everyone tracking them. */
    @Override
    public void setChanged() {
        if (this.player instanceof ServerPlayer serverPlayer) {
            serverPlayer.syncData(ModAttachments.LIGHT_ITEM);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.put(ItemStack.EMPTY);
    }
}
