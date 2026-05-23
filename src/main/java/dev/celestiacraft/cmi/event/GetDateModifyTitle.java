package dev.celestiacraft.cmi.event;

import dev.celestiacraft.libs.NebulaLibs;
import dev.celestiacraft.libs.utils.FestivalUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GetDateModifyTitle {
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			String title;

			if (FestivalUtils.isAprilFoolsDay()) {
				title = "Create: Infinity Mechanism";
			} else {
				title = "Create: Mechanism and Innovation";
			}

			String greeting = FestivalUtils.getFestivalGreeting();

			if (greeting != null) {
				title += " - " + greeting;
			}

			NebulaLibs.modifyWindowsTitle(title);
		});
	}
}