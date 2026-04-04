package dev.celestiacraft.cmi.event;

import dev.celestiacraft.cmi.Cmi;
import dev.celestiacraft.cmi.config.CommonConfig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cmi.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SneakyLink {
	@SubscribeEvent
	public static void onSneakyLink(TickEvent.PlayerTickEvent event) {
		Player player = event.player;
		Level level = player.level();
		boolean pressed = player.getPersistentData().getBoolean("cmi_sneaky_link");

		double radius = CommonConfig.COLLECTION_RADIUS.get();
		double depth = CommonConfig.COLLECTION_DEPTH.get();
		double height = CommonConfig.COLLECTION_HEIGHT.get();

		if (!level.isClientSide()
				&& player.tickCount % 4 == 0
				&& event.phase == TickEvent.Phase.END
				&& pressed
		) {
			AABB boundingBox = new AABB(
					player.getX() - radius,
					player.getY() - depth,
					player.getZ() - radius,
					player.getX() + radius,
					player.getY() + height,
					player.getZ() + radius
			);
			for (ItemEntity entity : level.getEntitiesOfClass(
					ItemEntity.class,
					boundingBox
			)) {
				if (CommonConfig.INSTANT_PICKUP.get()) {
					entity.setNoPickUpDelay();
				}
				entity.playerTouch(player);
			}
		}
	}
}