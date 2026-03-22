package dev.celestiacraft.cmi.common.block.test_coke_oven;

import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.api.register.multiblock.ControllerBlockEntity;
import dev.celestiacraft.cmi.api.register.multiblock.MultiblockContext;
import dev.celestiacraft.cmi.common.register.CmiMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TestCokeOvenBlockEntity extends ControllerBlockEntity {
	private int workTimer = 0;
	private ItemStack input = ItemStack.EMPTY;
	private ItemStack output = ItemStack.EMPTY;

	public TestCokeOvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, CmiMultiblock.TEST_COKE_OVEN);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, TestCokeOvenBlockEntity entity) {
		if (level.isClientSide()) {
			entity.runRecipe();
		}
	}

	public void runRecipe() {
		if (level == null || level.isClientSide()) {
			return;
		}

		int timeToWork = 20;
		boolean canWork = isStructureValid() && input.is(ItemTags.LOGS) && output.getCount() < 64;

		if (!canWork) {
			workTimer = 0;
			return;
		}

		workTimer++;
		setChanged();

		if (workTimer >= timeToWork) {
			workTimer = 0;
			input.shrink(1);
			output = output.copy();
			output.grow(1);
			setChanged();
		}
	}

	@Override
	protected String getMultiblockKey() {
		return String.format("multiblock.building.%s.test_coke_oven", Cmi.MODID);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("Input", input.save(new CompoundTag()));
		tag.put("Output", output.save(new CompoundTag()));
		tag.putInt("WorkTimer", workTimer);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		input = ItemStack.of(tag.getCompound("Input"));
		output = ItemStack.of(tag.getCompound("Output"));
		workTimer = tag.getInt("WorkTimer");
	}
}