package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redp.tutorialmod.TutorialMod;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoveC2SPacket.PositionAndOnGround.class)
public abstract class ExampleMixin {


	private ClientPlayerEntity client = MinecraftClient.getInstance().player;

	@Inject(method = "write", at = @At("TAIL"), cancellable = true)
	private void beforeWrite(PacketByteBuf buf, CallbackInfo ci) {
		double x = buf.readDouble();
		double y = buf.readDouble();
		double z = buf.readDouble();
		// Modify the values
		x = Math.round(x);
		y = Math.round(y);
		z = Math.round(z);
		short onground = buf.readUnsignedByte();
		// Reset the buffer's reader index
		buf.setIndex(0,0);
		// Write the modified values back to the buffer
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.readDouble();
		buf.readDouble();
		buf.readDouble();
	}
}
