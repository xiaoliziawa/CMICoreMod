package dev.celestiacraft.cmi.utils;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import com.simibubi.create.Create;
import dev.celestiacraft.cmi.Cmi;
import earth.terrarium.adastra.AdAstra;
import mekanism.common.Mekanism;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;

public class ModResources {
	public static final ResourceLocation TREATED_WOOD_SLAB;
	public static final ResourceLocation SEA_WATER;

	static {
		TREATED_WOOD_SLAB = loadIE("slab_treated_wood_horizontal");
		SEA_WATER = loadCmi("sea_water");
	}

	public static ResourceLocation loadCmi(String path) {
		return Cmi.loadResource(path);
	}

	public static ResourceLocation loadMek(String path) {
		return Mekanism.rl(path);
	}

	public static ResourceLocation loadCreate(String path) {
		return Create.asResource(path);
	}

	public static ResourceLocation loadTCon(String path) {
		return TConstruct.getResource(path);
	}

	public static ResourceLocation loadIE(String path) {
		return ImmersiveEngineering.rl(path);
	}

	public static ResourceLocation loadAd(String path) {
		return ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, path);
	}
}