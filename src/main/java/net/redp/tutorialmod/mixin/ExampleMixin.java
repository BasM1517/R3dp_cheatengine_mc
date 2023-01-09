package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.redp.tutorialmod.TutorialMod;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoveC2SPacket.PositionAndOnGround.class)
public abstract class ExampleMixin{

	private ClientPlayerEntity client = MinecraftClient.getInstance().player;
	private  double xPlayer = Math.round(client.getX());
	private  double zPlayer = Math.round(client.getZ());

	//@Inject(method = "write", at = @At("TAIL"))
	private void beforeWrite(PacketByteBuf buf, CallbackInfo ci) {
		// Modify the x and z fields of the PlayerMoveC2SPacket
		//TutorialModClient.logInfo(client.getX() + " " + client.getZ() + " " + xPlayer % 10 + zPlayer % 10 + " rounded : " + xPlayer + zPlayer);
		buf.clear();
		long x = (long) (xPlayer * 1000) % 10;
		long z = (long) (zPlayer * 1000) % 10;
		buf.writeDouble(xPlayer);
		buf.writeDouble(client.getY());
		buf.writeDouble(zPlayer);
		buf.writeByte(1);
		TutorialModClient.logInfo(String.valueOf(buf.readableBytes()));
		TutorialModClient.logInfo(String.valueOf(buf.readDouble()));
		TutorialModClient.logInfo(String.valueOf(buf.readDouble()));
		TutorialModClient.logInfo(String.valueOf(buf.readDouble()));
		TutorialModClient.logInfo(String.valueOf(buf.readUnsignedByte()));
	}
}
