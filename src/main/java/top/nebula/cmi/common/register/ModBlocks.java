package top.nebula.cmi.common.register;

import com.tterrag.registrate.util.entry.BlockEntry;
import top.nebula.cmi.Cmi;
import top.nebula.cmi.common.block.custom.*;

public class ModBlocks {
	public static final BlockEntry<GoldenSaplingBlock> GOLD_SAPLING;
	public static final BlockEntry<WaterPumpBlock> WATER_PUMP;
	public static final BlockEntry<MarsGeothermalVentBlock> MARS_GEO;
	public static final BlockEntry<MercuryGeothermalVentBlock> MERCURY_GEO;
	public static final BlockEntry<TestGravelBlock> TEST_GRAVEL;

	static {
		TEST_GRAVEL = Cmi.REGISTRATE.block("test_gravel", TestGravelBlock::new)
				.item()
				.build()
				.register();
		GOLD_SAPLING = Cmi.REGISTRATE.block("gold_sapling", GoldenSaplingBlock::new)
				.item()
				.build()
				.register();
		WATER_PUMP = Cmi.REGISTRATE.block("water_pump", WaterPumpBlock::new)
				.item()
				.build()
				.register();
		MARS_GEO = Cmi.REGISTRATE.block("mars_geothermal_vent", MarsGeothermalVentBlock::new)
				.item()
				.build()
				.register();
		MERCURY_GEO = Cmi.REGISTRATE.block("mercury_geothermal_vent", MercuryGeothermalVentBlock::new)
				.item()
				.build()
				.register();
	}

	public static void register() {
		Cmi.LOGGER.info("CMI Core Blocks Registered!");
	}
}