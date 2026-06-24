package dev.celestiacraft.cmi.common.item.mechanism;

import dev.celestiacraft.cmi.common.item.MechanismItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BronzeItem extends MechanismItem {
	public BronzeItem(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResultHolder<ItemStack> onMechanismUse(Level level, Player player, InteractionHand usedHand) {
		level.playSound(
				null,
				player.getX(),
				player.getY(),
				player.getZ(),
				SoundEvents.FIRE_EXTINGUISH,
				SoundSource.PLAYERS,
				1.0F,
				1.0F
		);

		if (!level.isClientSide()) {
			player.hurt(player.damageSources().hotFloor(), 8.0F);
		}

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide());
	}
}