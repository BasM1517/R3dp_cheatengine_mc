package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoveC2SPacket.LookAndOnGround.class)
public class LookandOnGroundMixin {


    @Inject(method = "write", at = @At("RETURN"))
    private void beforeWrite(PacketByteBuf buf, CallbackInfo ci) {
        buf.clear();
        buf.writeFloat(0.0f);
        buf.writeFloat(0.0f);
        buf.writeByte(true ? 1 : 0);

        TutorialModClient.logInfo(String.valueOf(buf.readFloat()));
        TutorialModClient.logInfo(String.valueOf(buf.readFloat()));
        TutorialModClient.logInfo(String.valueOf(buf.readUnsignedByte()));
    }
}
