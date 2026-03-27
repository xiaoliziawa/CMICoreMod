package dev.celestiacraft.cmi.common.register;

import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.common.recipe.accelerator.AcceleratorRecipe;
import dev.celestiacraft.cmi.common.recipe.space_elevator_base.SpaceElevatorBaseRecipe;
import dev.celestiacraft.cmi.common.recipe.space_elevator_construction.SpaceElevatorConstructionRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CmiRecipeSerializer {
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS;
	public static final Supplier<RecipeSerializer<AcceleratorRecipe>> ACCELERATOR;
	public static final Supplier<RecipeSerializer<SpaceElevatorBaseRecipe>> SPACE_ELEVATOR_BASE;
	public static final Supplier<RecipeSerializer<SpaceElevatorConstructionRecipe>> SPACE_ELEVATOR_CONSTRUCTION;

	static {
		SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Cmi.MODID);

		ACCELERATOR = SERIALIZERS.register("accelerator", () -> {
			return AcceleratorRecipe.Serializer.INSTANCE;
		});
		SPACE_ELEVATOR_BASE = SERIALIZERS.register("space_elevator_base", () -> {
			return SpaceElevatorBaseRecipe.Serializer.INSTANCE;
		});
		SPACE_ELEVATOR_CONSTRUCTION = SERIALIZERS.register("space_elevator_construction", () -> {
			return SpaceElevatorConstructionRecipe.Serializer.INSTANCE;
		});
	}

	public static void register(IEventBus bus) {
		SERIALIZERS.register(bus);
		Cmi.LOGGER.info("CMI Core RecipeSerializers Registered!");
	}
}
