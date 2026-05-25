package dev.celestiacraft.cmi.common.block.space_elevator_base_console.transfer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.eventbus.api.Event;

public class SpaceElevatorConsoleFluidTransferEvent extends Event {
	private final ServerLevel level;
	private final BlockPos consolePos;
	private final int amount;

	public SpaceElevatorConsoleFluidTransferEvent(ServerLevel level, BlockPos consolePos, int amount) {
		this.level = level;
		this.consolePos = consolePos.immutable();
		this.amount = amount;
	}

	public ServerLevel level() {
		return level;
	}

	public BlockPos consolePos() {
		return consolePos;
	}

	public int amount() {
		return amount;
	}
}
