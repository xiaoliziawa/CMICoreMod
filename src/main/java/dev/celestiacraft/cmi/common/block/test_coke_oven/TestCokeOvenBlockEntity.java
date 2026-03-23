package dev.celestiacraft.cmi.common.block.test_coke_oven;

import blusunrize.immersiveengineering.common.register.IEFluids;
import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.api.register.multiblock.ControllerBlockEntity;
import dev.celestiacraft.cmi.api.register.multiblock.IControllerRecipe;
import dev.celestiacraft.cmi.api.register.multiblock.MultiblockContext;
import dev.celestiacraft.cmi.common.block.test_coke_oven.capability.CokeOvenFluidCapability;
import dev.celestiacraft.cmi.common.block.test_coke_oven.capability.CokeOvenItemCapability;
import dev.celestiacraft.cmi.common.register.CmiMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class TestCokeOvenBlockEntity extends ControllerBlockEntity implements IControllerRecipe {
	private CokeOvenItemCapability itemHandler;
	private CokeOvenFluidCapability fluidHandler;

	private int workTimer = 0;

	public TestCokeOvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, CmiMultiblock.TEST_COKE_OVEN);
	}

	/**
	 * 延迟初始化
	 */
	private void initHandlers() {
		if (level == null) {
			return;
		}
		if (itemHandler != null) {
			return;
		}

		if (level.getBlockEntity(this.getBlockPos().below()) instanceof TestCokeOvenIOBlockEntity entity) {
			itemHandler = new CokeOvenItemCapability(entity);
			fluidHandler = new CokeOvenFluidCapability(entity);
		}
	}

	@Override
	public void tick(MultiblockContext context) {
		if (!context.isClient()) {
			runRecipe(context);
		}
	}

	@Override
	public void runRecipe(MultiblockContext context) {
		if (context.getLevel() == null || context.isClient()) {
			return;
		}

		initHandlers();
		if (itemHandler == null || fluidHandler == null) {
			return;
		}

		ItemStack input = itemHandler.getStackInSlot(0);
		ItemStack output = itemHandler.getStackInSlot(1);

		int timeToWork = 20;

		boolean canWork = isStructureValid()
				&& input.is(ItemTags.LOGS)
				&& output.getCount() < 64;

		if (!canWork) {
			workTimer = 0;
			return;
		}

		workTimer++;
		setChanged();

		if (workTimer >= timeToWork) {
			workTimer = 0;

			input.shrink(1);

			itemHandler.insertItem(1, new ItemStack(Items.CHARCOAL), false);

			fluidHandler.fill(
					new FluidStack(IEFluids.CREOSOTE.getStill(), 125),
					IFluidHandler.FluidAction.EXECUTE
			);
		}
	}

	@Override
	protected String getMultiblockKey() {
		return String.format("multiblock.building.%s.test_coke_oven", Cmi.MODID);
	}

	/**
	 * Controller 只存自己的数据
	 *
	 * @param tag
	 */
	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);

		tag.putInt("WorkTimer", workTimer);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);

		workTimer = tag.getInt("WorkTimer");
	}
}