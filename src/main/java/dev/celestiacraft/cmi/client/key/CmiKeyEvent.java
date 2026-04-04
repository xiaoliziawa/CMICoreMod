package dev.celestiacraft.cmi.client.key;

import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.network.CmiNetwork;
import dev.celestiacraft.cmi.network.c2s.CmiKeyPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cmi.MODID, value = Dist.CLIENT)
public class CmiKeyEvent {
	private static boolean lastState = false;

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			boolean current = CmiKeyBindings.SNEAKY_LINK.isDown();

			// 只在状态变化时发包
			if (current != lastState) {
				CmiNetwork.CHANNEL.sendToServer(new CmiKeyPacket(current));
				lastState = current;
			}
		}
	}
}