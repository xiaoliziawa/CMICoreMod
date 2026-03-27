package dev.celestiacraft.cmi.network.c2s;

import dev.celestiacraft.cmi.common.recipe.space_elevator_construction.SpaceElevatorConstructionRecipe;
import dev.celestiacraft.cmi.compat.adastra.SpaceElevatorConstructionHandler;
import dev.celestiacraft.cmi.compat.adastra.SpaceElevatorMaterialStorage;
import dev.celestiacraft.cmi.network.CmiNetwork;
import dev.celestiacraft.cmi.network.s2c.SyncSpaceElevatorMaterialsPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class StoreSpaceElevatorMaterialsPacket {
	private final BlockPos anchorPos;

	public StoreSpaceElevatorMaterialsPacket(BlockPos anchorPos) {
		this.anchorPos = anchorPos.immutable();
	}

	public static void encode(StoreSpaceElevatorMaterialsPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.anchorPos);
	}

	public static StoreSpaceElevatorMaterialsPacket decode(FriendlyByteBuf buf) {
		return new StoreSpaceElevatorMaterialsPacket(buf.readBlockPos());
	}

	public static void handle(StoreSpaceElevatorMaterialsPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> {
			ServerPlayer player = ctx.getSender();
			if (player == null) {
				return;
			}
			if (!SpaceElevatorConstructionHandler.isWrench(player.getMainHandItem())) {
				return;
			}
			if (!SpaceElevatorConstructionHandler.isWithinUseRange(player, msg.anchorPos)) {
				player.displayClientMessage(Component.translatable("text.cmi.space_elevator.too_far"), false);
				return;
			}
			if (!SpaceElevatorConstructionHandler.isAnchorBlock(player.serverLevel(), msg.anchorPos)) {
				player.displayClientMessage(Component.translatable("text.cmi.space_elevator.invalid_anchor"), false);
				return;
			}

			SpaceElevatorConstructionRecipe recipe = SpaceElevatorConstructionHandler.getRecipe(player.serverLevel());
			if (recipe == null) {
				player.displayClientMessage(Component.translatable("text.cmi.space_elevator.no_recipe"), false);
				return;
			}

			SpaceElevatorMaterialStorage.StoreResult result = SpaceElevatorMaterialStorage.storeFromInventory(player, msg.anchorPos, recipe);
			player.displayClientMessage(Component.translatable(result.translationKey()), false);
			CmiNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncSpaceElevatorMaterialsPacket(msg.anchorPos, SpaceElevatorConstructionHandler.getStoredCounts(player.serverLevel(), msg.anchorPos, recipe.ingredients().size())));
		});
		ctx.setPacketHandled(true);
	}
}
