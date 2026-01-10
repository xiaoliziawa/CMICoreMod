package top.nebula.cmi.common.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import top.nebula.cmi.Cmi;
import top.nebula.cmi.common.block.entity.*;

public class ModBlockEntityTypes {
	public static final BlockEntityEntry<TestGravelBlockEntity> TEST_GRAVEL;
	public static final BlockEntityEntry<MarsGeothermalVentBlockEntity> MARS_GEO;
	public static final BlockEntityEntry<MercuryGeothermalVentBlockEntity> MERCURY_GEO;
	public static final BlockEntityEntry<WaterPumpBlockEntity> WATER_PUMP;

	static {
		TEST_GRAVEL = Cmi.REGISTRATE.blockEntity("test_gravel", TestGravelBlockEntity::new)
				.validBlock(ModBlocks.TEST_GRAVEL)
				.register();
		MARS_GEO = Cmi.REGISTRATE.blockEntity("mars_geothermal_vent", MarsGeothermalVentBlockEntity::new)
				.validBlock(ModBlocks.MARS_GEO)
				.register();
		MERCURY_GEO = Cmi.REGISTRATE.blockEntity("mercury_geothermal_vent", MercuryGeothermalVentBlockEntity::new)
				.validBlock(ModBlocks.MERCURY_GEO)
				.register();
		WATER_PUMP = Cmi.REGISTRATE.blockEntity("water_pump", WaterPumpBlockEntity::new)
				.validBlock(ModBlocks.WATER_PUMP)
				.register();
	}

	public static void register() {
		Cmi.LOGGER.info("CMI Core BlockEntities Registered!");
	}
}