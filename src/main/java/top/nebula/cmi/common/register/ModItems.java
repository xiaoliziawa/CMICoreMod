package top.nebula.cmi.common.register;

import com.tterrag.registrate.util.entry.ItemEntry;
import top.nebula.cmi.Cmi;
import top.nebula.cmi.common.item.NuclearMechanism;
import top.nebula.cmi.common.item.TestBrush;

public class ModItems {
	public static final ItemEntry<NuclearMechanism> NUCLEAR_MECHANISM;
	public static final ItemEntry<TestBrush> TEST_BRUSH;

	static {
		TEST_BRUSH = Cmi.REGISTRATE.item("test_brush", TestBrush::new)
				.register();
		NUCLEAR_MECHANISM = Cmi.REGISTRATE.item("nuclear_mechanism", NuclearMechanism::new)
				.register();
	}

	public static void register() {
		Cmi.LOGGER.info("CMI Core Items Registered!");
	}
}