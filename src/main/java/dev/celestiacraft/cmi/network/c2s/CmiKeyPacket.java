package dev.celestiacraft.cmi.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CmiKeyPacket {
	private final boolean pressed;

	public CmiKeyPacket(boolean pressed) {
		this.pressed = pressed;
	}

	public static void encode(CmiKeyPacket packet, FriendlyByteBuf buf) {
		buf.writeBoolean(packet.pressed);
	}

	public static CmiKeyPacket decode(FriendlyByteBuf buf) {
		return new CmiKeyPacket(buf.readBoolean());
	}

	public static void handle(CmiKeyPacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayer player = context.get().getSender();
			if (player != null) {
				player.getPersistentData().putBoolean("cmi_sneaky_link", msg.pressed);
			}
		});
		context.get().setPacketHandled(true);
	}
}