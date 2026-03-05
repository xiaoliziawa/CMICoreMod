package dev.celestiacraft.cmi.event;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import dev.celestiacraft.cmi.Cmi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerDrownInNether {
	@SubscribeEvent
	public static void onLivingBreathe(LivingBreatheEvent event) {
		LivingEntity entity = event.getEntity();
		if (!(entity instanceof Player player)) {
			return;
		}

		Level level = player.level();
		if (level.dimension() != Level.NETHER) {
			return;
		}

		if (player.isCreative() || player.isSpectator()) {
			return;
		}

		ItemStack divingHelmet = DivingHelmetItem.getWornItem(player);
		List<ItemStack> backtanks = BacktankUtil.getAllWithAir(player);
		boolean hasBacktankSupport = !divingHelmet.isEmpty() && !backtanks.isEmpty();

		if (hasBacktankSupport) {
			event.setCanBreathe(true);
			event.setCanRefillAir(true);
			event.setConsumeAirAmount(0);
			event.setRefillAirAmount(player.getMaxAirSupply());
			if (!level.isClientSide() && level.getGameTime() % 20 == 0) {
				BacktankUtil.consumeAir(player, backtanks.get(0), 1);
			}
			return;
		}

		event.setCanBreathe(false);
		event.setCanRefillAir(false);
		event.setConsumeAirAmount(1);
		event.setRefillAirAmount(0);
	}
}
