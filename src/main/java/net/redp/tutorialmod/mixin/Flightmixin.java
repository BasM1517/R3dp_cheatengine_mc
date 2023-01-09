package net.redp.tutorialmod.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class Flightmixin {
    @Inject(method = "sendMovementPackets", at = @At("HEAD"), cancellable = true)
    private void onSendMovementPackets(CallbackInfo ci) {
        // Get the player entity
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        // Get the player's current position
        Vec3d currentPosition = player.getPos();

        // Calculate the new position
        double x = Math.round(currentPosition.getX());
        double y = Math.round(currentPosition.getY());
        double z = (long) (currentPosition.getZ() * 1000) % 10;

        // Check if the player's position needs to be changed
        if (currentPosition.getX() != x || currentPosition.getY() != y || currentPosition.getZ() != z) {
            // Set the player's position
            player.setPosition(x, y, z);

            // Cancel the original sendMovementPackets() method
            //ci.cancel();
        }
    }
}
