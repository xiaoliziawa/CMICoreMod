package dev.celestiacraft.cmi.compat.mbd2;

import com.lowdragmc.mbd2.api.recipe.ingredient.FluidIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class MBDFluidIngredient extends FluidIngredient {
	private MBDFluidIngredient() {
		super(Stream.empty(), 0, null);
	}

	public static FluidIngredient ofTag(TagKey<Fluid> tag, long amount) {
		return FluidIngredient.of(tag, amount);
	}

	public static FluidIngredient ofTag(TagKey<Fluid> tag, long amount, @Nullable CompoundTag nbt) {
		return FluidIngredient.of(tag, amount, nbt);
	}

	public static FluidIngredient ofTagId(ResourceLocation tag, long amount) {
		return FluidIngredient.of(FluidTags.create(tag), amount);
	}

	public static FluidIngredient ofTagId(ResourceLocation tag, long amount, @Nullable CompoundTag nbt) {
		return FluidIngredient.of(FluidTags.create(tag), amount, nbt);
	}
}