package dev.celestiacraft.cmi.common.recipe.freezing;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class FreezingWrapper extends RecipeWrapper {
	public FreezingWrapper(IItemHandlerModifiable inv) {
		super(new ItemStackHandler(1));
	}
}