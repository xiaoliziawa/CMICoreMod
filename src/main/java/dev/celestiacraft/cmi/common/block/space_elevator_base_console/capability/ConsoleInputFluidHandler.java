package dev.celestiacraft.cmi.common.block.space_elevator_base_console.capability;

import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class ConsoleInputFluidHandler implements IFluidHandler {
	private final FluidTank[] tanks;

	public ConsoleInputFluidHandler(FluidTank[] tanks) {
		this.tanks = tanks;
	}

	@Override
	public int getTanks() {
		return tanks.length;
	}

	@Override
	public @NotNull FluidStack getFluidInTank(int tank) {
		if (tank < 0 || tank >= tanks.length) {
			return FluidStack.EMPTY;
		}
		return tanks[tank].getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		if (tank < 0 || tank >= tanks.length) {
			return 0;
		}
		return tanks[tank].getCapacity();
	}

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
		return !stack.getFluid().isSame(Fluids.EMPTY);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		if (resource.isEmpty()) {
			return 0;
		}
		int matchingIndex = -1;
		for (int i = 0; i < tanks.length; i++) {
			FluidStack existing = tanks[i].getFluid();
			if (!existing.isEmpty() && existing.isFluidEqual(resource)) {
				matchingIndex = i;
				break;
			}
		}
		if (matchingIndex >= 0) {
			return tanks[matchingIndex].fill(resource, action);
		}
		for (FluidTank tank : tanks) {
			if (tank.getFluid().isEmpty()) {
				int filled = tank.fill(resource, action);
				if (filled > 0) {
					return filled;
				}
			}
		}
		return 0;
	}

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
		return FluidStack.EMPTY;
	}

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
		return FluidStack.EMPTY;
	}
}
