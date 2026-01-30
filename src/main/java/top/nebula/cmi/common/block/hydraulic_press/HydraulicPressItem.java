package top.nebula.cmi.common.block.hydraulic_press;

import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import top.nebula.cmi.config.CommonConfig;
import top.nebula.cmi.utils.CmiLang;

import javax.annotation.Nullable;
import java.util.List;

public class HydraulicPressItem extends AssemblyOperatorBlockItem {
	public HydraulicPressItem(Block block, Properties properties) {
		super(block, properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
		int steamCost = CommonConfig.HYDRAULIC_PRESS_STEAM_CONSUMPTION.get();

		Lang.translate("tooltip.holdForDescription",
						Component.literal("Shift").withStyle(Screen.hasShiftDown() ? ChatFormatting.WHITE : ChatFormatting.GRAY))
				.style(ChatFormatting.DARK_GRAY)
				.addTo(tooltip);

		if (Screen.hasShiftDown()) {
			tooltip.add(Component.empty());

			CmiLang.translate("tooltip.hydraulic_press.summary")
					.style(ChatFormatting.GRAY)
					.addTo(tooltip);

			tooltip.add(Component.empty());

			CmiLang.translate("tooltip.hydraulic_press.condition1")
					.style(ChatFormatting.GRAY)
					.addTo(tooltip);

			CmiLang.translate("tooltip.hydraulic_press.behaviour1", steamCost)
					.style(ChatFormatting.DARK_GRAY)
					.addTo(tooltip);

			CmiLang.translate("tooltip.hydraulic_press.behaviour2", steamCost)
					.style(ChatFormatting.DARK_GRAY)
					.addTo(tooltip);
		}
	}
}