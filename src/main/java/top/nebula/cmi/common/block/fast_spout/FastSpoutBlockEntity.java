package top.nebula.cmi.common.block.fast_spout;

import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.PASS;

import java.util.ArrayList;
import java.util.List;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.fluids.spout.FillingBySpout;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import top.nebula.cmi.config.CommonConfig;

/**
 * A faster version of Create's Spout that uses configurable filling time.
 * Inherits SpoutBlockEntity so that BlockSpoutingBehaviour.fillBlock() type check passes.
 * Default filling time is 6 ticks (vs original 20 ticks).
 */
public class FastSpoutBlockEntity extends SpoutBlockEntity {

	public FastSpoutBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	/**
	 * Get the configurable filling time.
	 * Minimum 6 to ensure the processingTicks == 5 completion check works.
	 */
	public int getFillingTime() {
		return Math.max(CommonConfig.FAST_SPOUT_FILLING_TIME.get(), 6);
	}

	private SmartFluidTankBehaviour getTank() {
		return getBehaviour(SmartFluidTankBehaviour.TYPE);
	}

	private FluidStack getCurrentFluid() {
		return getTank().getPrimaryHandler().getFluid();
	}

	/**
	 * Override whenItemHeld to use configurable filling time instead of FILLING_TIME(20).
	 */
	@Override
	protected ProcessingResult whenItemHeld(TransportedItemStack transported,
			TransportedItemStackHandlerBehaviour handler) {
		if (processingTicks != -1 && processingTicks != 5)
			return HOLD;
		if (!FillingBySpout.canItemBeFilled(level, transported.stack))
			return PASS;
		if (getTank().isEmpty())
			return HOLD;
		FluidStack fluid = getCurrentFluid();
		int requiredAmountForItem = FillingBySpout.getRequiredAmountForItem(level, transported.stack, fluid.copy());
		if (requiredAmountForItem == -1)
			return PASS;
		if (requiredAmountForItem > fluid.getAmount())
			return HOLD;

		if (processingTicks == -1) {
			processingTicks = getFillingTime();
			notifyUpdate();
			AllSoundEvents.SPOUTING.playOnServer(level, worldPosition, 0.75f, 0.9f + 0.2f * (float) Math.random());
			return HOLD;
		}

		// Process finished
		ItemStack out = FillingBySpout.fillItem(level, requiredAmountForItem, transported.stack, fluid);
		if (!out.isEmpty()) {
			List<TransportedItemStack> outList = new ArrayList<>();
			TransportedItemStack held = null;
			TransportedItemStack result = transported.copy();
			result.stack = out;
			if (!transported.stack.isEmpty())
				held = transported.copy();
			outList.add(result);
			handler.handleProcessingOnItem(transported, TransportedResult.convertToAndLeaveHeld(outList, held));
		}

		getTank().getPrimaryHandler().setFluid(fluid);
		sendSplash = true;
		notifyUpdate();
		return HOLD;
	}

	/**
	 * Override tick to intercept and correct the FILLING_TIME used by super.tick().
	 *
	 * super.tick() flow when starting customProcess:
	 *   1. Sets processingTicks = FILLING_TIME (20)
	 *   2. Then processingTicks-- → becomes 19
	 *
	 * We detect this transition (from -1 to active) and correct the value
	 * to our configurable filling time minus the 1 tick already decremented.
	 */
	@Override
	public void tick() {
		boolean wasIdle = (processingTicks == -1);
		super.tick();
		// If super.tick() just started a customProcess cycle (from -1 → FILLING_TIME-1 = 19)
		if (wasIdle && processingTicks == FILLING_TIME - 1) {
			processingTicks = getFillingTime() - 1;
		}
	}
}
