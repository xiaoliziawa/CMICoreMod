package top.nebula.cmi.client.block.resource;

import com.jozufozu.flywheel.core.PartialModel;
import top.nebula.cmi.Cmi;

public class CmiBlockPartialModel {
	public static final PartialModel STEAM_HAMMER;

	static {
		STEAM_HAMMER = addPartial("steam_hammer/head");
	}

	private static PartialModel addPartial(String path) {
		return new PartialModel(Cmi.loadResource("block/" + path));
	}

	public static void init() {
	}
}