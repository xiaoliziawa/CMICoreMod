package dev.celestiacraft.cmi.common.block.test_multiblock;

import com.simibubi.create.foundation.block.IBE;
import dev.celestiacraft.cmi.common.register.CmiBlockEntityTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

public class TestMultiblockBlock extends Block implements IBE<TestMultiblockBlockEntity> {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	public TestMultiblockBlock(Properties properties) {
		super(Properties.copy(Blocks.STONE)
				.sound(SoundType.STONE));
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH));
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	public Class<TestMultiblockBlockEntity> getBlockEntityClass() {
		return TestMultiblockBlockEntity.class;
	}

	public BlockEntityType<? extends TestMultiblockBlockEntity> getBlockEntityType() {
		return CmiBlockEntityTypes.TEST_MULTIBLOCK.get();
	}
}
