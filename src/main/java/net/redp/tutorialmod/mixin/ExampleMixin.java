package net.redp.tutorialmod.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.redp.tutorialmod.TutorialMod;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class ExampleMixin extends Screen{
		protected ExampleMixin(Text title) {
			super(title);
		}

		//@Inject(at = @At("RETURN"), method = "initWidgetsNormal")
		public void addCustomButton(int y, int spacingY, CallbackInfo cl) {
			// add a button to toggle the flight feature
			this.addDrawableChild(new ButtonWidget(this.width / 2 - 102 + 205, y, 98, 20, Text.translatable("Flyhacks"), (button) -> {

			}));
		}
	}
