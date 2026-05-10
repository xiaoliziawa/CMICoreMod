package dev.celestiacraft.cmi.common.item.mechanism;

import dev.celestiacraft.cmi.common.item.MechanismItem;
import dev.celestiacraft.cmi.common.register.CmiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class CopperItem extends MechanismItem {
	public CopperItem(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult onMechanismUse(UseOnContext context) {
		Level level = context.getLevel();
		Player player = context.getPlayer();
		InteractionHand hand = context.getHand();
		ItemStack item = context.getItemInHand();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		HitResult hitResult = player.pick(5.0d, 0.0f, false);

		if (hitResult instanceof BlockHitResult blockHit) {
			if (state.is(CmiBlock.ACCELERATOR.get())) {
				player.swing(hand);
				return InteractionResult.SUCCESS;
			}
		}

		ThrownPotion potion = new ThrownPotion(level, player);
		ItemStack potionStack = Items.SPLASH_POTION.getDefaultInstance();

		PotionUtils.setPotion(potionStack, Potions.WATER);
		potion.setItem(potionStack);

		potion.shootFromRotation(
				player,
				player.getXRot(),
				player.getYRot(),
				0.0F,
				1.0F,
				1.0F
		);
		level.playSound(
				null,
				player.getX(),
				player.getY(),
				player.getZ(),
				SoundEvents.ARROW_SHOOT,
				SoundSource.PLAYERS,
				0.5F,
				0.3F
		);

		player.swing(hand);
		return InteractionResult.SUCCESS;
	}
}