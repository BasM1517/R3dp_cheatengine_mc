package net.redp.tutorialmod.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
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
    private void addCustomButton(CallbackInfo ci){
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102 -100, this.height / 4 + 48 + -16, 98, 20, Text.translatable("Flyhacks"), (button) -> {
            this.client.setScreen(new AdvancementsScreen(this.client.player.networkHandler.getAdvancementHandler()));
        }));
    }
}
