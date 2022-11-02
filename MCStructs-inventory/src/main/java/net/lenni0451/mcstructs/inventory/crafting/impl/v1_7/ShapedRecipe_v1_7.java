package net.lenni0451.mcstructs.inventory.crafting.impl.v1_7;

import net.lenni0451.mcstructs.inventory.crafting.IRecipe;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class ShapedRecipe_v1_7<I> implements IRecipe<I, LegacyItemStack<I>> {

    private final int width;
    private final int height;
    private final LegacyItemStack<I>[] ingredients;
    private final LegacyItemStack<I> result;
    private boolean copyNbt = false;

    public ShapedRecipe_v1_7(final int width, final int height, final LegacyItemStack<I>[] ingredients, final LegacyItemStack<I> result) {
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = result;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public LegacyItemStack<?>[] getIngredients() {
        return this.ingredients;
    }

    public LegacyItemStack<?> getResult() {
        return this.result;
    }

    public ShapedRecipe_v1_7<I> setCopyNbt() {
        this.copyNbt = true;
        return this;
    }

    @Override
    public boolean matches(ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        for (int x = 0; x <= 3 - this.width; x++) {
            for (int y = 0; y <= 3 - this.height; y++) {
                if (this.matches(craftingInventory, x, y, true)) return true;
                if (this.matches(craftingInventory, x, y, false)) return true;
            }
        }
        return false;
    }

    private boolean matches(final ICraftingInventory<I, LegacyItemStack<I>> craftingInventory, final int x, final int y, final boolean reverse) {
        for (int xOffset = 0; xOffset < 3; xOffset++) {
            for (int yOffset = 0; yOffset < 3; yOffset++) {
                int ix = xOffset - x;
                int iy = yOffset - y;

                LegacyItemStack<I> ingredient = null;
                if (ix >= 0 && iy >= 0 && ix < this.width && iy < this.height) {
                    if (reverse) ingredient = this.ingredients[this.width - ix - 1 + iy * this.width];
                    else ingredient = this.ingredients[ix + iy * this.width];
                }

                LegacyItemStack<I> slot = craftingInventory.getStack(xOffset, yOffset);
                if (slot != null || ingredient != null) {
                    if (slot == null || ingredient == null) return false;
                    if (ingredient.getItem().equals(slot.getItem())) return false;
                    if (ingredient.getDamage() != 32767 && ingredient.getDamage() != slot.getDamage()) return false;
                }
            }
        }
        return true;
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> result = this.result.copy();
        if (this.copyNbt) {
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    LegacyItemStack<I> stack = craftingInventory.getStack(x, y);
                    if (stack != null && stack.hasTag()) result.setTag(stack.copyTag());
                }
            }
        }
        return result;
    }

}
