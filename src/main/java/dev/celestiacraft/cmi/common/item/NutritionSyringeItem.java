package dev.celestiacraft.cmi.common.item;

import dev.celestiacraft.libs.api.client.context.TooltipContext;
import dev.celestiacraft.libs.api.register.item.BasicItem;
import dev.celestiacraft.libs.compat.curios.CuriosContext;
import dev.celestiacraft.libs.compat.curios.ICuriosHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;

public class NutritionSyringeItem extends BasicItem implements ICuriosHelper {
	public NutritionSyringeItem(Properties properties) {
		super(properties.stacksTo(1)
				.rarity(Rarity.EPIC));
	}

	@Override
	public void onCuriosTick(CuriosContext context) {
		Player player = context.getPlayer();

		player.addEffect(new MobEffectInstance(
				MobEffects.SATURATION,
				-1,
				255,
				false,
				false,
				false
		));
	}

	@Override
	public int tickCheck() {
		return 20 * 60;
	}

	@Override
	public void addTooltips(TooltipContext context) {
		context.addTranslatable("cmi.tooltip.nutrition_syringe");
	}
}