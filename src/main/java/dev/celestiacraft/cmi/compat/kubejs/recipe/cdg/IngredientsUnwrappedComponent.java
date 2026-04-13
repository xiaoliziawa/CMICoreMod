package dev.celestiacraft.cmi.compat.kubejs.recipe.cdg;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentWithParent;

public class IngredientsUnwrappedComponent implements RecipeComponentWithParent<Either<InputFluid, InputItem>[]> {
	@Override
	public RecipeComponent<Either<InputFluid, InputItem>[]> parentComponent() {
		return CdgRecipesSchema.INGREDIENTS.component;
	}

	@Override
	public JsonElement write(RecipeJS recipe, Either<InputFluid, InputItem>[] value) {
		JsonArray json = new JsonArray();

		for (Either<InputFluid, InputItem> either : value) {
			either.ifLeft((fluid) -> {
				json.add(FluidComponents.INPUT.write(recipe, fluid));
			});

			either.ifRight((item) -> {
				for (InputItem unwrapped : item.unwrap()) {
					json.add(ItemComponents.INPUT.write(recipe, unwrapped));
				}
			});
		}

		return json;
	}
}