package dev.celestiacraft.cmi.common.item.mechanism;

import dev.celestiacraft.cmi.api.curios.IMechanismCurios;
import dev.celestiacraft.cmi.common.item.MechanismItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NetherItem extends MechanismItem implements IMechanismCurios {
	public NetherItem(Properties properties) {
		super(properties);
	}

	@Override
	public void curiosTick(Player player, Level level) {
		player.addEffect(new MobEffectInstance(
				MobEffects.FIRE_RESISTANCE,
				20 * 20,
				0,
				false,
				false
		));
	}
}