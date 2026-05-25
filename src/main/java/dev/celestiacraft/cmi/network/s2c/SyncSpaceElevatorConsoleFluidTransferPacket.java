package dev.celestiacraft.cmi.network.s2c;

import dev.celestiacraft.cmi.client.render.SpaceElevatorConsoleScreenState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSpaceElevatorConsoleFluidTransferPacket {
	private final BlockPos consolePos;

	public SyncSpaceElevatorConsoleFluidTransferPacket(BlockPos consolePos) {
		this.consolePos = consolePos.immutable();
	}

	public static void encode(SyncSpaceElevatorConsoleFluidTransferPacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.consolePos);
	}

	public static SyncSpaceElevatorConsoleFluidTransferPacket decode(FriendlyByteBuf buf) {
		return new SyncSpaceElevatorConsoleFluidTransferPacket(buf.readBlockPos());
	}

	public static void handle(SyncSpaceElevatorConsoleFluidTransferPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SpaceElevatorConsoleScreenState.markFluidTransfer(msg.consolePos)));
		ctx.setPacketHandled(true);
	}
}
