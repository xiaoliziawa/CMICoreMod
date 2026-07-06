package dev.celestiacraft.cmi.common.block.test_gravel;

import com.simibubi.create.foundation.block.IBE;
import dev.celestiacraft.cmi.common.register.CmiBlockEntity;
import dev.celestiacraft.libs.api.register.block.BasicBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TestGravelBlock extends BasicBlock implements IBE<TestGravelBlockEntity> {
	public TestGravelBlock(Properties properties) {
		super(Properties.copy(Blocks.GRAVEL));
		registerDefaultState(stateDefinition.any().setValue(DUSTED, 0));
	}

	public static final IntegerProperty DUSTED = IntegerProperty.create("dusted", 0, 3);

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DUSTED);
	}

	@Override
	public Class<TestGravelBlockEntity> getBlockEntityClass() {
		return TestGravelBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends TestGravelBlockEntity> getBlockEntityType() {
		return CmiBlockEntity.TEST_GRAVEL.get();
	}
}