package dev.celestiacraft.cmi.client.key;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class CmiKeyBindings {
	public static final KeyMapping SNEAKY_LINK = new KeyMapping(
			"key.cmi.sneaky_link",
			GLFW.GLFW_KEY_V,
			"key.cmi.categories"
	);
}