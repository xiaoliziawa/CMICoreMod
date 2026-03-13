package dev.celestiacraft.cmi.common.item.mechanism;

import dev.celestiacraft.cmi.api.curios.IMechanismCurios;
import dev.celestiacraft.cmi.common.item.MechanismItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CobaltItem extends MechanismItem implements IMechanismCurios {
	public CobaltItem(Properties properties) {
		super(properties);
	}

	@Override
	public void curiosTick(Player player, Level level) {
		player.addEffect(new MobEffectInstance(
				MobEffects.MOVEMENT_SPEED,
				20 * 20,
				1,
				false,
				false
		));
		player.addEffect(new MobEffectInstance(
				MobEffects.DIG_SPEED,
				20 * 20,
				1,
				false,
				false
		));
	}
}