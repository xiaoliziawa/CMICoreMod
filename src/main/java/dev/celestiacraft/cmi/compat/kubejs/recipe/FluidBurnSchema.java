package dev.celestiacraft.cmi.compat.kubejs.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface FluidBurnSchema {
	RecipeKey<Double> HU = NumberComponent.DOUBLE.key("hu");
	RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid");

	RecipeSchema SCHEMA = new RecipeSchema(FLUID, HU);
}