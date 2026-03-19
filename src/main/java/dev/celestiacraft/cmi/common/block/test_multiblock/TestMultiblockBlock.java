package dev.celestiacraft.cmi.common.block.test_multiblock;

import dev.celestiacraft.cmi.api.register.multiblock.ControllerBlock;
import dev.celestiacraft.cmi.api.register.multiblock.ControllerBlockFacing;
import dev.celestiacraft.cmi.common.register.CmiBlockEntityTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TestMultiblockBlock extends ControllerBlock<TestMultiblockBlockEntity> {
	public TestMultiblockBlock(Properties properties) {
		super(Properties.copy(Blocks.STONE));
	}

	@Override
	protected ControllerBlockFacing useFacingType() {
		return ControllerBlockFacing.HORIZONTAL;
	}

	@Override
	public Class<TestMultiblockBlockEntity> getBlockEntityClass() {
		return TestMultiblockBlockEntity.class;
	}

	public BlockEntityType<? extends TestMultiblockBlockEntity> getBlockEntityType() {
		return CmiBlockEntityTypes.TEST_MULTIBLOCK.get();
	}
}