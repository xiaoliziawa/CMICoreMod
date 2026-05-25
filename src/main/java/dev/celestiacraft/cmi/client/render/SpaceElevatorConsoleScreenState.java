package dev.celestiacraft.cmi.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class SpaceElevatorConsoleScreenState {
	private static final int ACTIVE_DISPLAY_TICKS = 22;
	private static final int FINISHED_DISPLAY_TICKS = 100;
	private static final Map<BlockPos, FluidTransferState> FLUID_TRANSFER_STATES = new HashMap<>();

	private SpaceElevatorConsoleScreenState() {
	}

	public static void markFluidTransfer(BlockPos pos) {
		long gameTime = getClientGameTime();
		FLUID_TRANSFER_STATES.put(pos.immutable(), new FluidTransferState(gameTime + ACTIVE_DISPLAY_TICKS, gameTime + ACTIVE_DISPLAY_TICKS + FINISHED_DISPLAY_TICKS));
	}

	public static boolean isFluidTransferActive(BlockPos pos) {
		FluidTransferState state = FLUID_TRANSFER_STATES.get(pos);
		return state != null && getClientGameTime() < state.activeUntilTick;
	}

	public static boolean isFluidTransferFinished(BlockPos pos) {
		FluidTransferState state = FLUID_TRANSFER_STATES.get(pos);
		if (state == null) {
			return false;
		}
		long gameTime = getClientGameTime();
		if (gameTime >= state.finishedUntilTick) {
			cleanupExpired(gameTime);
			return false;
		}
		return gameTime >= state.activeUntilTick;
	}

	private static long getClientGameTime() {
		if (Minecraft.getInstance().level == null) {
			return 0L;
		}
		return Minecraft.getInstance().level.getGameTime();
	}

	private static void cleanupExpired(long gameTime) {
		Iterator<Map.Entry<BlockPos, FluidTransferState>> iterator = FLUID_TRANSFER_STATES.entrySet().iterator();
		while (iterator.hasNext()) {
			if (gameTime >= iterator.next().getValue().finishedUntilTick) {
				iterator.remove();
			}
		}
	}

	private record FluidTransferState(long activeUntilTick, long finishedUntilTick) {
	}
}
