package dev.celestiacraft.cmi.network.s2c;

import dev.celestiacraft.cmi.event.SpaceElevatorWrenchClientHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSpaceElevatorMaterialsPacket {
	private final BlockPos anchorPos;
	private final int[] counts;

	public SyncSpaceElevatorMaterialsPacket(BlockPos anchorPos, int[] counts) {
		this.anchorPos = anchorPos.immutable();
		this.counts = counts.clone();
	}

	public static void encode(SyncSpaceElevatorMaterialsPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.anchorPos);
		buf.writeVarInt(msg.counts.length);
		for (int count : msg.counts) {
			buf.writeVarInt(count);
		}
	}

	public static SyncSpaceElevatorMaterialsPacket decode(FriendlyByteBuf buf) {
		BlockPos anchorPos = buf.readBlockPos();
		int size = buf.readVarInt();
		int[] counts = new int[size];
		for (int i = 0; i < size; i++) {
			counts[i] = buf.readVarInt();
		}
		return new SyncSpaceElevatorMaterialsPacket(anchorPos, counts);
	}

	public static void handle(SyncSpaceElevatorMaterialsPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> SpaceElevatorWrenchClientHandler.syncStoredCounts(msg.anchorPos, msg.counts));
		ctx.setPacketHandled(true);
	}
}
