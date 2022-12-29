package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.redp.tutorialmod.TutorialMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen{

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgets")
    public void addCustomButton( CallbackInfo cl) {
        // add a button to toggle the flight feature
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102 + 205, this.height / 4 + 24 + -16, 98, 20, Text.translatable("Flyhacks"), (button) -> {
            TutorialMod.toggleFlightButton();
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102 + 205, this.height / 4 + 24 + 8, 98, 20, Text.translatable("AutoplaceFarm"), (button) -> {
            TutorialMod.toggleAutoFarmButton();
        }));
    }
}
