package dev.celestiacraft.cmi.common.block.stock_manager;

import com.simibubi.create.content.logistics.stockTicker.StockTickerInteractionHandler;
import com.simibubi.create.foundation.block.IBE;
import dev.celestiacraft.cmi.api.interaction.UseContext;
import dev.celestiacraft.cmi.api.register.block.BasicBlock;
import dev.celestiacraft.cmi.common.register.CmiBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class StockManagerBlock extends BasicBlock implements IBE<StockManagerBlockEntity> {

	public StockManagerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Class<StockManagerBlockEntity> getBlockEntityClass() {
		return StockManagerBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends StockManagerBlockEntity> getBlockEntityType() {
		return CmiBlockEntity.STOCK_MANAGER.get();
	}

	@Override
	public InteractionResult useOn(UseContext context) {
		Player player = context.getPlayer();
		Level level = context.getLevel();

		if (context.isClient()) {
			return InteractionResult.SUCCESS;
		}

		BlockEntity be = level.getBlockEntity(context.getPos());

		if (!(be instanceof StockManagerBlockEntity manager)) {
			return InteractionResult.PASS;
		}

		if (manager.behaviour == null || manager.behaviour.freqId == null) {
			player.displayClientMessage(
					Component.literal("未连接物流网络"),
					true
			);
			return InteractionResult.SUCCESS;
		}

		boolean success = StockTickerInteractionHandler.interactWithLogisticsManagerAt(
				player,
				level,
				context.getPos()
		);

		if (!success) {
			player.displayClientMessage(
					Component.literal("无法打开物流界面"),
					true
			);
		}

		return InteractionResult.SUCCESS;
	}
}