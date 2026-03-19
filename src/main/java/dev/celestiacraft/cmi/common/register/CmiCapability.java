package dev.celestiacraft.cmi.common.register;

import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.common.block.void_dust_collector.capability.VDCEnergyStorage;
import dev.celestiacraft.cmi.common.block.void_dust_collector.capability.VDCItemHandler;
import dev.celestiacraft.cmi.common.block.void_dust_collector.capability.VDCItmeCapability;
import dev.celestiacraft.cmi.common.block.water_pump.capability.WaterPumpFluidCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CmiCapability {
	@SubscribeEvent
	public static void registerCapability(RegisterCapabilitiesEvent event) {
		// 水井
		event.register(WaterPumpFluidCapability.class);
		// 虚空粉末收集器
		event.register(VDCEnergyStorage.class);
		event.register(VDCItemHandler.class);
		event.register(VDCItmeCapability.class);
	}
}