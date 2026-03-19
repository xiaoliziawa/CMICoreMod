package dev.celestiacraft.cmi.common.register;

import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.common.block.water_pump.capability.WaterPumpFluidCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CmiCapability {
	@SubscribeEvent
	public static void registerCapability(RegisterCapabilitiesEvent event) {
		event.register(WaterPumpFluidCapability.class);
	}
}