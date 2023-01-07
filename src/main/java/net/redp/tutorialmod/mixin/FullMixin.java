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

    static ClientPlayerEntity client = MinecraftClient.getInstance().player;
    private static double oldx = 0.0d;
    private static double oldz = 0.0d;
    private static double xPlayer = client.getX();
    private static double zPlayer = client.getZ();


    @Inject(method = "write", at = @At("RETURN"))
    private void beforeWrite(PacketByteBuf buf, CallbackInfo ci) {
        // Modify the x and z fields of the PlayerMoveC2SPacket
        buf.clear();
        if (xPlayer % 10 == 0.0 && zPlayer % 10 == 0.0){
            buf.writeDouble(xPlayer);
            buf.writeDouble(client.getY());
            buf.writeDouble(zPlayer);
            buf.writeFloat(0);
            buf.writeFloat(0);
            buf.writeByte(true ? 1 : 0);
            oldx = xPlayer;
            oldz = zPlayer;
        }else{
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
