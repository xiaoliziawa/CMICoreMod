package dev.celestiacraft.cmi.utils;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import com.simibubi.create.Create;
import dev.celestiacraft.cmi.Cmi;
import earth.terrarium.adastra.AdAstra;
import lombok.Getter;
import mekanism.common.Mekanism;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.TConstruct;

import java.util.function.Supplier;

public class ModResources {
	public static final Entry TREATED_WOOD_SLAB;
	public static final Entry SEA_WATER;
	public static final Entry NAHUATL_SLAB;
	public static final Entry NAHUATL_FENCE;
	public static final Entry BLAZEWOOD_SLAB;
	public static final Entry BLAZEWOOD_FENCE;

	static {
		TREATED_WOOD_SLAB = loadIE("slab_treated_wood_horizontal");
		SEA_WATER = loadCmi("sea_water");
		NAHUATL_SLAB = loadTCon("nahuatl_slab");
		NAHUATL_FENCE = loadTCon("nahuatl_fence");
		BLAZEWOOD_SLAB = loadTCon("blazewood_slab");
		BLAZEWOOD_FENCE = loadTCon("blazewood_fence");
	}

	public static Entry loadResource(ResourceLocation id) {
		return new Entry(id);
	}

	public static Entry loadCmi(String path) {
		return loadResource(Cmi.loadResource(path));
	}

	public static Entry loadMek(String path) {
		return loadResource(Mekanism.rl(path));
	}

	public static Entry loadCreate(String path) {
		return loadResource(Create.asResource(path));
	}

	public static Entry loadTCon(String path) {
		return loadResource(TConstruct.getResource(path));
	}

	public static Entry loadIE(String path) {
		return loadResource(ImmersiveEngineering.rl(path));
	}

	public static Entry loadAd(String path) {
		return loadResource(ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, path));
	}

	public static class Entry {
		@Getter
		private final ResourceLocation location;

		private Supplier<Item> itemCache;
		private Supplier<Block> blockCache;
		private Supplier<Fluid> fluidCache;

		public Entry(ResourceLocation location) {
			this.location = location;
		}

		public Item getItem() {
			if (itemCache == null) {
				itemCache = () -> {
					return ForgeRegistries.ITEMS.getValue(location);
				};
			}
			return itemCache.get();
		}

		public Block getBlock() {
			if (blockCache == null) {
				blockCache = () -> {
					return ForgeRegistries.BLOCKS.getValue(location);
				};
			}
			return blockCache.get();
		}

		public Fluid getFluid() {
			if (fluidCache == null) {
				fluidCache = () -> {
					return ForgeRegistries.FLUIDS.getValue(location);
				};
			}
			return fluidCache.get();
		}

		public FluidStack getFluidStack(int amount) {
			return new FluidStack(getFluid(), amount);
		}

		@Override
		public String toString() {
			return location.toString();
		}
	}
}