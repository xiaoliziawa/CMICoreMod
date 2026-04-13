package dev.celestiacraft.cmi.common.block.stock_manager;

import com.simibubi.create.content.logistics.packagerLink.LogisticallyLinkedBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class StockManagerBlockEntity extends SmartBlockEntity {
	public LogisticallyLinkedBehaviour behaviour;

	public StockManagerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(behaviour = new LogisticallyLinkedBehaviour(this, false));
	}

	@Override
	public void lazyTick() {
		super.lazyTick();
		LogisticallyLinkedBehaviour.keepAlive(behaviour);
	}
}