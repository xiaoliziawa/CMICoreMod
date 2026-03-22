package dev.celestiacraft.cmi.api.register.multiblock;

import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockContext<T extends ControllerBlockEntity> {
	@Getter
	private final T entity;
	@Getter
	private final Level level;
	@Getter
	private final ItemStack stack;
	@Getter
	private final int workTimes;
	@Getter
	private final BlockPos pos;
	@Getter
	private final BlockState state;

	public MultiblockContext(T entity, Level level, ItemStack stack, int workTimes, BlockPos pos, BlockState state) {
		this.entity = entity;
		this.level = level;
		this.stack = stack;
		this.workTimes = workTimes;
		this.pos = pos;
		this.state = state;
	}

	public boolean isClient() {
		return level.isClientSide();
	}
}