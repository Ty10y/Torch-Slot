package com.torchslot;

import java.util.function.BooleanSupplier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** The light slot added to every {@code InventoryMenu}. */
public class LightSlot extends Slot {
    /** Index of this slot in {@code InventoryMenu}: appended after the offhand slot (45). */
    public static final int MENU_INDEX = 46;
    /** Position in the survival inventory, relative to the GUI: a pop-out tab left of the chestplate slot. */
    public static final int X = -18;
    public static final int Y = 26;
    public static final Identifier EMPTY_ICON = Identifier.fromNamespaceAndPath(TorchSlot.MOD_ID, "container/slot/lantern");

    /**
     * Whether the slot is shown on the client (it hides behind the "+" button until opened).
     * Set by the client; the server always treats the slot as active.
     */
    public static BooleanSupplier clientVisible = () -> true;

    private final Player owner;

    public LightSlot(Player owner, int x, int y) {
        super(new LightSlotContainer(owner), 0, x, y);
        this.owner = owner;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return LightSources.isLightSource(stack);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public Identifier getNoItemIcon() {
        return EMPTY_ICON;
    }

    @Override
    public boolean isActive() {
        return !this.owner.level().isClientSide() || clientVisible.getAsBoolean();
    }
}
