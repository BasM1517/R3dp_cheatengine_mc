package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(ClientPlayerEntity.class)
public abstract class Flightmixin {

    @Shadow
    private boolean lastSprinting;
    @Shadow
    private boolean lastSneaking;
    @Shadow
    private boolean lastOnGround;
    @Shadow
    private boolean autoJumpEnabled;
    @Shadow
    private double lastX;
    @Shadow
    private double lastBaseY;
    @Shadow
    private double lastZ;
    @Shadow
    private float lastPitch;
    @Shadow
    private float lastYaw;
    @Shadow
    private int ticksSinceLastPositionPacketSent;


    //@Inject(method = "sendMovementPackets", at = @At("HEAD"),cancellable = true )
    private void Movement(CallbackInfo ci) {

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        boolean bl = player.isSprinting();
        if (bl != lastSprinting) {
            ClientCommandC2SPacket.Mode mode = bl ? ClientCommandC2SPacket.Mode.START_SPRINTING : ClientCommandC2SPacket.Mode.STOP_SPRINTING;
            player.networkHandler.sendPacket(new ClientCommandC2SPacket(player, mode));
            lastSprinting = bl;
        }

        boolean bl2 = player.isSneaking();
        if (bl2 != lastSneaking) {
            ClientCommandC2SPacket.Mode mode2 = bl2 ? ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY : ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY;
            player.networkHandler.sendPacket(new ClientCommandC2SPacket(player, mode2));
            lastSneaking = bl2;
        }

        if (true) {
            double d = player.getX() - lastX;
            double e = player.getY() - lastBaseY;
            double f = player.getZ() - lastZ;
            double g = (double)(player.getYaw() - lastYaw);
            double h = (double)(player.getPitch() - lastPitch);
            ++ticksSinceLastPositionPacketSent;
            boolean bl3 = MathHelper.squaredMagnitude(d, e, f) > MathHelper.square(2.0E-4) || ticksSinceLastPositionPacketSent >= 20;
            boolean bl4 = g != 0.0 || h != 0.0;
            if (player.hasVehicle()) {
                Vec3d vec3d = player.getVelocity();
                player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(vec3d.x, -999.0, vec3d.z, player.getYaw(), player.getPitch(), player.isOnGround()));
                bl3 = false;
            } else if (bl3 && bl4) {
                player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(Math.round(player.getX()), player.getY(),Math.round( player.getZ()), player.getYaw(), player.getPitch(),player.isOnGround()));
            } else if (bl3) {
                player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(Math.round(player.getX()), player.getY(), Math.round(player.getZ()), player.isOnGround()));
            } else if (bl4) {
                player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(player.getYaw(), player.getPitch(), player.isOnGround()));
            } else if (lastOnGround != player.isOnGround()) {
                player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(player.isOnGround()));
            }

            if (bl3) {
                lastX = Math.round(player.getX());
                lastBaseY = Math.round(player.getY());
                lastZ = Math.round(player.getZ());
                ticksSinceLastPositionPacketSent = 0;
            }

            if (bl4) {
                lastYaw = player.getYaw();
                lastPitch = player.getPitch();
            }

            lastOnGround = player.isOnGround();
            autoJumpEnabled = (Boolean) MinecraftClient.getInstance().options.getAutoJump().getValue();
        }
        ci.cancel();
    }




}
