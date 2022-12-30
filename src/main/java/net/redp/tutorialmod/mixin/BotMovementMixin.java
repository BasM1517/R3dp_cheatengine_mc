package net.redp.tutorialmod.mixin;

import net.minecraft.entity.player.PlayerEntity;
        import org.spongepowered.asm.mixin.Mixin;
        import org.spongepowered.asm.mixin.injection.At;
        import org.spongepowered.asm.mixin.injection.Inject;
        import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class BotMovementMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this; // cast the Mixin object to PlayerEntity
        double x = player.getPos().x;
        double z = player.getPos().z;
        x = Math.round(x);
        z = Math.round(z);
        player.setPos(x, player.getPos().y, z);
    }
    // other fields and methods go here
}