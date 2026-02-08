package top.nebula.cmi.compat.jei.api;

import mezz.jei.api.recipe.RecipeType;
import top.nebula.cmi.Cmi;
import top.nebula.cmi.common.block.belt_grinder.GrindingRecipe;
import top.nebula.cmi.common.recipe.accelerator.AcceleratorRecipe;
import top.nebula.cmi.common.recipe.void_dust_collector.VoidDustCollectorRecipe;
import top.nebula.cmi.common.recipe.water_pump.WaterPumpRecipe;
import top.nebula.cmi.common.recipe.water_pump.WaterPumpSeaWaterRecipe;

public class CmiJeiRecipeType {
	public static final RecipeType<AcceleratorRecipe> ACCELERATOR;
	public static final RecipeType<WaterPumpRecipe> WATER_PUMP;
	public static final RecipeType<WaterPumpSeaWaterRecipe> SEA_WATER_PUMP;
	public static final RecipeType<GrindingRecipe> GRINDING;
	public static final RecipeType<VoidDustCollectorRecipe> VOID_DUST_COLLECTOR;

	static {
		ACCELERATOR = addJeiRecipeType("accelerator", AcceleratorRecipe.class);
		WATER_PUMP = addJeiRecipeType("water_pump", WaterPumpRecipe.class);
		SEA_WATER_PUMP = addJeiRecipeType("water_pump_sea_water", WaterPumpSeaWaterRecipe.class);
		GRINDING = addJeiRecipeType("grinding", GrindingRecipe.class);
		VOID_DUST_COLLECTOR = addJeiRecipeType("void_dust_collector", VoidDustCollectorRecipe.class);
	}

	private static <T> RecipeType<T> addJeiRecipeType(String path, Class<? extends T> recipeClass) {
		return RecipeType.create(Cmi.MODID, path, recipeClass);
	}
}