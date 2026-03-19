package dev.celestiacraft.cmi.common.block.test_coke_oven;

import dev.celestiacraft.cmi.api.register.multiblock.ControllerBlock;
import dev.celestiacraft.cmi.api.register.multiblock.ControllerBlockFacing;
import dev.celestiacraft.cmi.common.register.CmiBlockEntityTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TestCokeOvenBlock extends ControllerBlock<TestCokeOvenBlockEntity> {
	public TestCokeOvenBlock(Properties properties) {
		super(Properties.copy(Blocks.STONE));
	}

	@Override
	protected ControllerBlockFacing useFacingType() {
		return ControllerBlockFacing.HORIZONTAL;
	}

	@Override
	public Class<TestCokeOvenBlockEntity> getBlockEntityClass() {
		return TestCokeOvenBlockEntity.class;
	}

	public BlockEntityType<? extends TestCokeOvenBlockEntity> getBlockEntityType() {
		return CmiBlockEntityTypes.TEST_COKE_OVEN.get();
	}
}
