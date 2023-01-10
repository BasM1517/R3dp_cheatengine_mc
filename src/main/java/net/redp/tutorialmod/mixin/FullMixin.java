package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoveC2SPacket.Full.class)
public class FullMixin {

    private  ClientPlayerEntity client = MinecraftClient.getInstance().player;
    private  double xPlayer = Math.round(client.getX());
    private  double zPlayer = Math.round(client.getZ());

    @Inject(method = "write", at = @At("TAIL"))
    private void beforeWrite(PacketByteBuf buf, CallbackInfo ci) {
        // Modify the x and z fields of the PlayerMoveC2SPacket
        buf.clear();
        long x = (long) (xPlayer * 1000) % 10;
        long z = (long) (zPlayer * 1000) % 10;

        if (x == 0 && z % 10 == 0){
            TutorialModClient.logInfo("Fullmixin de goeie");
            buf.writeDouble(Math.round(client.getX()));
            buf.writeDouble(client.getY());
            buf.writeDouble(Math.round(client.getZ()));
            buf.writeFloat(client.headYaw);
            buf.writeFloat(client.getPitch());
            buf.writeByte(true ? 1 : 0);
        }else{
            TutorialModClient.logInfo("Fullmixin de foute");
            buf.writeDouble(0.0d);
            buf.writeDouble(60.0d);
            buf.writeDouble(0.0d);
            buf.writeFloat(0);
            buf.writeFloat(0);
            buf.writeByte(true ? 1 : 0);
        }

        TutorialModClient.logInfo(String.valueOf(buf.readDouble()));
        TutorialModClient.logInfo(String.valueOf(buf.readDouble()));
        TutorialModClient.logInfo(String.valueOf(buf.readDouble()));
        TutorialModClient.logInfo(String.valueOf(buf.readFloat()));
        TutorialModClient.logInfo(String.valueOf(buf.readFloat()));
        TutorialModClient.logInfo(String.valueOf(buf.readUnsignedByte()));
    }
}
