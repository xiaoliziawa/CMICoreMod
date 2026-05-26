package dev.celestiacraft.cmi.feature.cargogrid;

import com.mojang.brigadier.Command;
import dev.celestiacraft.cmi.Cmi;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CargoGridCommand {
	private CargoGridCommand() {}

	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(
				Commands.literal(Cmi.MODID)
						.then(Commands.literal("reload_grid")
								.requires(source -> source.hasPermission(2))
								.executes(ctx -> {
									int loaded = CargoGridRules.load();
									if (loaded < 0) {
										ctx.getSource().sendFailure(Component.literal("[CMI] Failed to reload cargo grid rules — see log"));
										return 0;
									}
									final int count = loaded;
									ctx.getSource().sendSuccess(
											() -> Component.literal("[CMI] Cargo grid: " + count + " rule(s) loaded"),
											true
									);
									return Command.SINGLE_SUCCESS;
								})
						)
		);
	}
}
