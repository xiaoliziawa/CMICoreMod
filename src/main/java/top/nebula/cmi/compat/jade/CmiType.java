package top.nebula.cmi.compat.jade;

import net.minecraft.resources.ResourceLocation;
import top.nebula.cmi.Cmi;

public class CmiType {
	public static final ResourceLocation COMMON = addType("common");

	public static ResourceLocation addType(String path) {
		return Cmi.loadResource(path);
	}
}