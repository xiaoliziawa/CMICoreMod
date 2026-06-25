package dev.celestiacraft.cmi.common.item.mechanism;

import dev.celestiacraft.cmi.common.item.MechanismItem;

public class RailwayItem extends MechanismItem {
	public RailwayItem(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean useAfterConsume() {
		return false;
	}
}