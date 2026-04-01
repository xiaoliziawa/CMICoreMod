package dev.celestiacraft.cmi.common.item.mechanism;

import dev.celestiacraft.cmi.common.item.MechanismItem;
import dev.celestiacraft.libs.compat.curios.CuriosContext;
import dev.celestiacraft.libs.compat.curios.ICuriosHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class PhotosensitiveItem extends MechanismItem implements ICuriosHelper {
	public PhotosensitiveItem(Properties properties) {
		super(properties);
	}

	@Override
	public void curiosTick(CuriosContext context) {
		context.getPlayer().addEffect(new MobEffectInstance(
				MobEffects.NIGHT_VISION,
				30 * 20,
				0,
				false,
				false
		));
	}
}