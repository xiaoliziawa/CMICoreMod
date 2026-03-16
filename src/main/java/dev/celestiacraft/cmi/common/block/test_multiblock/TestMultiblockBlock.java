package dev.celestiacraft.cmi.common.block.test_multiblock;

import com.lowdragmc.lowdraglib.test.TestBlockEntity;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.block.IBE;
import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.common.block.water_pump.WaterPumpBlockEntity;
import dev.celestiacraft.cmi.common.register.CmiBlock;
import dev.celestiacraft.cmi.common.register.CmiBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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

	@SubscribeEvent
	public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		Player player = event.getEntity();
		InteractionHand hand = event.getHand();
		ItemStack item = player.getItemInHand(hand);
		BlockState state = level.getBlockState(pos);

		if (!level.isClientSide()) {
			return;
		}

		boolean isBlockAndItem = state.is(CmiBlock.TEST_MULTIBLOCK.get())
				&& item.is(AllTags.AllItemTags.WRENCH.tag);

		if (isBlockAndItem && hand == InteractionHand.MAIN_HAND && !player.isShiftKeyDown()) {
			TestMultiblockBlockEntity entity = (TestMultiblockBlockEntity) level.getBlockEntity(pos);
			if (entity != null) {
				player.swing(hand);
				entity.showMultiblock();
			}
		}
	}
}
