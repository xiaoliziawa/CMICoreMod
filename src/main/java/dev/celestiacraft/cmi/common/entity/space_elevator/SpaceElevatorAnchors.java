package dev.celestiacraft.cmi.common.entity.space_elevator;

import dev.celestiacraft.cmi.common.block.space_elevator_base_console.SpaceElevatorBaseConsoleBlockEntity;
import dev.celestiacraft.cmi.common.block.space_elevator_top.SpaceElevatorTopBlockEntity;
import dev.celestiacraft.cmi.common.register.CmiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public final class SpaceElevatorAnchors {
	private static final int SEARCH_RADIUS = 12;
	private static final int SEARCH_HEIGHT = 16;

	private SpaceElevatorAnchors() {
	}

	public static boolean isValidAnchor(Level level, BlockPos pos) {
		return level.getBlockState(pos).is(CmiBlock.SPACE_ELEVATOR_BASE_CONSOLE.get())
				|| level.getBlockState(pos).is(CmiBlock.SPACE_ELEVATOR_TOP.get());
	}

	public static boolean isTopAnchor(Level level, BlockPos pos) {
		return level.getBlockState(pos).is(CmiBlock.SPACE_ELEVATOR_TOP.get());
	}

	@Nullable
	public static BlockEntity findEnergySource(ServerLevel level, BlockPos anchorPos) {
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
		for (int dy = -SEARCH_HEIGHT; dy <= SEARCH_HEIGHT; dy++) {
			for (int dx = -SEARCH_RADIUS; dx <= SEARCH_RADIUS; dx++) {
				for (int dz = -SEARCH_RADIUS; dz <= SEARCH_RADIUS; dz++) {
					cursor.set(anchorPos.getX() + dx, anchorPos.getY() + dy, anchorPos.getZ() + dz);
					BlockEntity be = level.getBlockEntity(cursor);
					if (be instanceof SpaceElevatorBaseConsoleBlockEntity) {
						return be;
					}
					if (be instanceof SpaceElevatorTopBlockEntity) {
						return be;
					}
				}
			}
		}
		return null;
	}

	public static int getEnergyStored(BlockEntity source) {
		if (source instanceof SpaceElevatorBaseConsoleBlockEntity console) {
			return console.getEnergyStored();
		}
		if (source instanceof SpaceElevatorTopBlockEntity top) {
			return top.getEnergyStored();
		}
		return 0;
	}

	public static boolean consumeLaunchEnergy(BlockEntity source) {
		if (source instanceof SpaceElevatorBaseConsoleBlockEntity console) {
			return console.consumeEnergy(SpaceElevatorBaseConsoleBlockEntity.LAUNCH_ENERGY_COST);
		}
		if (source instanceof SpaceElevatorTopBlockEntity top) {
			return top.consumeEnergy(SpaceElevatorTopBlockEntity.LAUNCH_ENERGY_COST);
		}
		return false;
	}

	public static int getLaunchEnergyCost(BlockEntity source) {
		if (source instanceof SpaceElevatorTopBlockEntity) {
			return SpaceElevatorTopBlockEntity.LAUNCH_ENERGY_COST;
		}
		return SpaceElevatorBaseConsoleBlockEntity.LAUNCH_ENERGY_COST;
	}

	public static void onElevatorArrived(ServerLevel level, BlockPos anchorPos) {
		if (level.getBlockEntity(anchorPos) instanceof SpaceElevatorTopBlockEntity top) {
			top.playCloseDoor();
		}
	}

	public static void onElevatorDeparting(ServerLevel level, BlockPos anchorPos) {
		if (level.getBlockEntity(anchorPos) instanceof SpaceElevatorTopBlockEntity top) {
			top.playOpenDoor();
		}
	}
}
