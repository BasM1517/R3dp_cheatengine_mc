package net.redp.tutorialmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class Flightmixin {
    private boolean flightEnabled = true;

    //@Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void toggleFlight(CallbackInfo ci) {
        if (flightEnabled) {
            // set flightEnabled to false and cancel the jump action
            flightEnabled = false;
            ci.cancel();
        } else {
            // set flightEnabled to true
            flightEnabled = true;
        }
    }

}
