package net.redp.tutorialmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.redp.tutorialmod.TutorialMod;
import net.redp.tutorialmod.TutorialModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import static net.redp.tutorialmod.TutorialMod.roundTheCoordinate;

@Mixin(PlayerMoveC2SPacket.PositionAndOnGround.class)
public class ExampleMixin {
	//als je je afvraag hoe deze methode is opgebouwd
	//@ModifyArgs ondat je een argument wilt aanpassen
	//method = "<init>" omdat je zodra hij gemaakt word wil je hem aanpassen
	//target = het pad dat getrokken word naar de file  + ;<init> omdat je hem wilt aanroepen als hij geiniteerd word +
	// (DDDFFZZZ)V waarom double x3 float x2 Boolean x3 V voor de return type
	@ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;<init>(DDDFFZZZ)V"))
	private static void init(Args args) {
		args.set(0, roundTheCoordinate(args.get(0)));  // rond x want hij is de 1ste die voorbij komt
		args.set(2, roundTheCoordinate(args.get(2)));  // rond z want hij is de 3de die voorbij komt
	}
}