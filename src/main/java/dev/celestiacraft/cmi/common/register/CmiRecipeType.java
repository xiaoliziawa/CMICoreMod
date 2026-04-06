package dev.celestiacraft.cmi.common.register;

import dev.celestiacraft.cmi.common.recipe.fluid_burn.FluidBurnRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.common.recipe.accelerator.AcceleratorRecipe;
import dev.celestiacraft.cmi.common.recipe.space_elevator_base.SpaceElevatorBaseRecipe;
import dev.celestiacraft.cmi.common.recipe.space_elevator_construction.SpaceElevatorConstructionRecipe;

import java.util.function.Supplier;

public class CmiRecipeType {
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES;
	public static final Supplier<RecipeType<AcceleratorRecipe>> ACCELERATOR;
	public static final Supplier<RecipeType<SpaceElevatorBaseRecipe>> SPACE_ELEVATOR_BASE;
	public static final Supplier<RecipeType<SpaceElevatorConstructionRecipe>> SPACE_ELEVATOR_CONSTRUCTION;
	public static final Supplier<RecipeType<FluidBurnRecipe>> FLUID_BURN;

	static {
		RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Cmi.MODID);

		ACCELERATOR = RECIPE_TYPES.register("accelerator", () -> {
			return AcceleratorRecipe.Type.INSTANCE;
		});
		SPACE_ELEVATOR_BASE = RECIPE_TYPES.register("space_elevator_base", () -> {
			return SpaceElevatorBaseRecipe.Type.INSTANCE;
		});
		SPACE_ELEVATOR_CONSTRUCTION = RECIPE_TYPES.register("space_elevator_construction", () -> {
			return SpaceElevatorConstructionRecipe.Type.INSTANCE;
		});
		FLUID_BURN = RECIPE_TYPES.register("fluid_burn", () -> {
			return FluidBurnRecipe.Type.INSTANCE;
		});
	}

	public static void register(IEventBus bus) {
		RECIPE_TYPES.register(bus);
		Cmi.LOGGER.info("CMI Core RecipeTypes Registered!");
	}
}
