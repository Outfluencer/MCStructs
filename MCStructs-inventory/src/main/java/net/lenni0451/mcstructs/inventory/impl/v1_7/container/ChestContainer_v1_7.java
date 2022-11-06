package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.SimpleInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class ChestContainer_v1_7<I> extends AContainer_v1_7<I> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final IInventory_v1_7<I> chestInventory;
    private final int rowCount;
    private final int rowSlotCount;

    public ChestContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I> playerInventory, final int count) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.chestInventory = new SimpleInventory_v1_7<>(count);
        this.rowCount = chestInventory.getSize() / 9;
        this.rowSlotCount = this.rowCount * 9;

        for (int i = 0; i < this.rowSlotCount; i++) this.addSlot(chestInventory, i, Slot.acceptAll());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public PlayerInventory_v1_7<I> getPlayerInventory() {
        return this.playerInventory;
    }

    public IInventory_v1_7<I> getChestInventory() {
        return this.chestInventory;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public int getRowSlotCount() {
        return this.rowSlotCount;
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        LegacyItemStack<I> slotStack = slot.getStack();
        LegacyItemStack<I> out = slotStack.copy();
        if (slotId < this.rowSlotCount) {
            if (!this.mergeStack(slotStack, this.rowSlotCount, this.getSlotCount(), true)) return null;
        } else if (!this.mergeStack(slotStack, 0, this.rowSlotCount, false)) return null;
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        return out;
    }

}
