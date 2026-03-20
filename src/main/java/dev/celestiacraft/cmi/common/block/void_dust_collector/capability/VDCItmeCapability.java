package dev.celestiacraft.cmi.common.block.void_dust_collector.capability;

import dev.celestiacraft.cmi.common.block.void_dust_collector.VoidDustCollectorBlockEnitiy;
import net.minecraftforge.items.ItemStackHandler;

public class VDCItmeCapability extends ItemStackHandler {
	private final VoidDustCollectorBlockEnitiy enitiy;

	public VDCItmeCapability(VoidDustCollectorBlockEnitiy enitiy) {
		super(1);
		this.enitiy = enitiy;
	}

	@Override
	protected void onContentsChanged(int slot) {
		enitiy.setChanged();
	}
}