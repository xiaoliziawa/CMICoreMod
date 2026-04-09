package dev.celestiacraft.cmi.api.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;

public interface IFluidInteractable {
	default boolean useFluidInteraction(UseContext context) {
		return false;
	}

	default	boolean creativeUseFluidInteraction(UseContext context) {
		return false;
	}

	default	boolean canUseFluidInteraction(UseContext context) {
		Player player = context.getPlayer();
		if (player == null) {
			return false;
		}

		return useFluidInteraction(context)
				|| (player.isCreative()
				&& creativeUseFluidInteraction(context));
	}

	default InteractionResult tryFluidInteraction(UseContext context) {
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		Level level = context.getLevel();
		BlockPos pos = context.getPos();
		BlockHitResult result = context.getResult();

		if (player == null) {
			return InteractionResult.PASS;
		}

		if (canUseFluidInteraction(context) && FluidUtil.interactWithFluidHandler(
				player,
				hand,
				level,
				pos,
				result.getDirection()
		)) {
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}
}