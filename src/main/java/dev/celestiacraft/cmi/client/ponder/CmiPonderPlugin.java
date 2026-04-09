package dev.celestiacraft.cmi.client.ponder;

import dev.celestiacraft.cmi.Cmi;
import net.createmod.ponder.api.registration.PonderPlugin;
import org.jetbrains.annotations.NotNull;

public class CmiPonderPlugin implements PonderPlugin {
	@Override
	public @NotNull String getModId() {
		return Cmi.MODID;
	}
}