package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redp.tutorialmod.PlayerMoveC2SPacketView;
import net.redp.tutorialmod.TutorialMod;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerMoveC2SPacket.Full.class)
public class FullMixin{
    private ClientPlayerEntity client = MinecraftClient.getInstance().player;
    @Inject(method = "write", at = @At("TAIL"),cancellable = true)
    private void beforeWrite(PacketByteBuf buf, CallbackInfo ci) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        float yaw = buf.readFloat();
        float pitch = buf.readFloat();
        short onground = buf.readUnsignedByte();


            buf.writeDouble(Math.round(x));
            buf.writeDouble(y);
            buf.writeDouble(Math.round(z));
            buf.writeFloat(yaw);
            buf.writeFloat(pitch);
            buf.writeByte(onground);
        TutorialModClient.logInfo("Fullmixin radablebytes : " + String.valueOf(buf.readableBytes()) + " x : " + buf.readDouble() + " y : " + buf.readDouble() + " z : " + buf.readDouble());
            //ci.cancel();
        }
    }
