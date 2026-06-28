package dev.celestiacraft.cmi.common.item;

import dev.celestiacraft.libs.api.register.item.energy.BasicEnergyItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimpleBatteryItem extends BasicEnergyItem {
	public SimpleBatteryItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public int getCapacity(ItemStack stack) {
		return 150000;
	}

	@Override
	public int getMaxReceive(ItemStack stack) {
		return 1000;
	}

	@Override
	public int getMaxExtract(ItemStack stack) {
		return 1000;
	}

	@Override
	public int getBarWidth(@NotNull ItemStack stack) {
		return getEnergyBarWidth(stack);
	}

	@Override
	public int getEnergyBarColor(ItemStack stack) {
		float ratio = getRatioBar(stack);

		if (ratio >= 0.6f) {
			return 0x00FFFF;
		} else if (ratio >= 0.2f) {
			return 0xFFFF00;
		} else {
			return 0xFF0000;
		}
	}
}