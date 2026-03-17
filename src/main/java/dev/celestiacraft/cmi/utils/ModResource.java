package dev.celestiacraft.cmi.utils;

import net.minecraft.resources.ResourceLocation;

public class ModResource {
	public static final ResourceLocation TREATED_WOOD_STAIRS;

	static {
		TREATED_WOOD_STAIRS = ResourceLocation.parse(
				"immersiveengineering:stairs_treated_wood_horizontal"
		);
	}
}