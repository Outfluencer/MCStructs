package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class CraftingResultInventory_v1_7<I> implements IInventory_v1_7<I> {

    private LegacyItemStack<I> result;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public LegacyItemStack<I> getStack(int slot) {
        if (slot != 0) throw new ArrayIndexOutOfBoundsException(slot);
        return this.result;
    }

    @Override
    public void setStack(int slot, LegacyItemStack<I> stack) {
        if (slot != 0) throw new ArrayIndexOutOfBoundsException(slot);
        this.result = stack;
    }

    @Override
    public LegacyItemStack<I> split(int slot, int count) {
        if (this.result != null) {
            LegacyItemStack<I> stack = this.result;
            this.result = null;
            return stack;
        }
        return null;
    }

}