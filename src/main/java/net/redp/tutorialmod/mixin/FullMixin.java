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
        double xbefore = buf.readDouble();
        double x = Math.round(xbefore);
        double y = buf.readDouble();
        double z = Math.round(buf.readDouble());
        TutorialModClient.logInfo("xyz : " + x + "xbefore " + xbefore + " " + y + " " + z);
        buf.setIndex(0,0);

        buf.writeDouble(Math.round(x));
        buf.writeDouble(y);
        buf.writeDouble(Math.round(z));
        buf.readDouble();
        buf.readDouble();
        buf.readDouble();
        }
    }
