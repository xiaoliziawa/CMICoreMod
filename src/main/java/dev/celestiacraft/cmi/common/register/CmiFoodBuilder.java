package dev.celestiacraft.cmi.common.register;

import dev.celestiacraft.libs.common.food.FoodBuilders;

public class CmiFoodBuilder {
	public static final FoodBuilders PIG_IRON;

	static {
		PIG_IRON = FoodBuilders.food((builder) -> {
			builder.nutrition(8)
					.saturationMod(1.0f)
					.alwaysEat()
					.fast();
		});
	}
}