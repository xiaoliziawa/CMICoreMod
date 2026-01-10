package top.nebula.cmi.common.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import top.nebula.cmi.Cmi;
import top.nebula.cmi.common.block.entity.*;

import java.util.function.Supplier;

public class ModBlockEntityTypes {
	public static final Supplier<BlockEntityType<TestGravelBlockEntity>> TEST_GRAVEL;
	public static final Supplier<BlockEntityType<MarsGeothermalVentBlockEntity>> MARS_GEO;
	public static final Supplier<BlockEntityType<MercuryGeothermalVentBlockEntity>> MERCURY_GEO;
	public static final BlockEntityEntry<WaterPumpBlockEntity> WATER_PUMP;

	static {
		TEST_GRAVEL = Cmi.REGISTRATE.blockEntity("test_gravel", TestGravelBlockEntity::new)
				.validBlocks(ModBlocks.TEST_GRAVEL)
				.register();
		MARS_GEO = Cmi.REGISTRATE.blockEntity("mars_geothermal_vent", MarsGeothermalVentBlockEntity::new)
				.validBlocks(ModBlocks.MARS_GEO)
				.register();
		MERCURY_GEO = Cmi.REGISTRATE.blockEntity("mercury_geothermal_vent", MercuryGeothermalVentBlockEntity::new)
				.validBlocks(ModBlocks.MERCURY_GEO)
				.register();
		WATER_PUMP = Cmi.REGISTRATE.blockEntity("water_pump", WaterPumpBlockEntity::new)
				.validBlocks(ModBlocks.WATER_PUMP)
				.register();
	}

	public static void register() {
		Cmi.LOGGER.info("CMI Core BlockEntities Registered!");
	}
}