package dev.celestiacraft.cmi.event;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.event.PipeCollisionEvent;
import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.api.server.ReplaceRule;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RandomGenerateStone {
	private static final List<Block> RANDOM_BLOCKS = List.of(
			Blocks.COBBLESTONE,
			Blocks.COBBLED_DEEPSLATE
	);

	private static final double CHANCE = 0.5;

	private static final List<ReplaceRule> REPLACE_BLOCK = List.of(
			new ReplaceRule(sb(Blocks.MAGMA_BLOCK), sb(Blocks.TUFF)),
			new ReplaceRule(List.of(sb(Blocks.SOUL_SAND), sb(Blocks.SOUL_SOIL)), sb(Blocks.BASALT)),
			new ReplaceRule(AllBlocks.ANDESITE_ALLOY_BLOCK, sb(Blocks.ANDESITE)),
			new ReplaceRule(sb(Blocks.QUARTZ_BLOCK), sb(Blocks.DIORITE))
	);

	@SubscribeEvent
	public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
		LevelAccessor level = event.getLevel();
		BlockPos pos = event.getPos();

		BlockState state = event.getNewState();
		Block block = state.getBlock();

		Block belowBlock = level.getBlockState(pos.below()).getBlock();

		if (!isTargetBlock(block)) {
			return;
		}

		RandomSource random = level.getRandom();

		for (ReplaceRule rule : REPLACE_BLOCK) {
			if (rule.matches(belowBlock)) {
				Block newBlock = random.nextDouble() < CHANCE
						? rule.getResult()
						: getRandomBlock(random);

				event.setNewState(newBlock.defaultBlockState());
				return;
			}
		}
	}

	@SubscribeEvent
	public static void onPipeSpill(PipeCollisionEvent.Spill event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();

		BlockState state = event.getState();
		Block block = state.getBlock();

		Block belowBlock = level.getBlockState(pos.below()).getBlock();

		if (!isTargetBlock(block)) {
			return;
		}

		RandomSource random = level.getRandom();

		for (ReplaceRule rule : REPLACE_BLOCK) {
			if (rule.matches(belowBlock)) {
				Block newBlock = random.nextDouble() < CHANCE
						? rule.getResult()
						: getRandomBlock(random);

				event.setState(newBlock.defaultBlockState());
				return;
			}
		}
	}

	private static boolean isTargetBlock(Block block) {
		return block == Blocks.COBBLESTONE || block == Blocks.STONE;
	}

	private static Block getRandomBlock(RandomSource random) {
		return RANDOM_BLOCKS.get(random.nextInt(RANDOM_BLOCKS.size()));
	}

	private static Supplier<Block> sb(Block block) {
		return () -> block;
	}
}