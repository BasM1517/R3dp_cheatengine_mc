package net.redp.tutorialmod;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.logging.Logger;


public class TutorialMod implements ModInitializer {

    public static final String MOD_ID = "TutorialMod";

    public static final String Mod_NAME = "TutorialMod";
    private static final Logger LOGGER = null;

    public static boolean flightenabled = false;
    public static boolean botmovementenabled = false;
    public static boolean autofarmenabled = false;
    @Override
    public void onInitialize() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null){
                player.getAbilities().flying = flightenabled;
                if (flightenabled){
                    player.setVelocity(player.getVelocity().x,player.getVelocity().y - 0.04,player.getVelocity().z);
                    //checkItemInHand(player);
                }
                if(autofarmenabled){
                    tick(player);
                }
            }
        });
    }
    public static void toggleFlightButton() {
        // call the toggleFlight method of the TutorialMod Mixin class
        flightenabled = !flightenabled;
    }
    public static void toggleBotMovementButton() {
        // call the toggleFlight method of the TutorialMod Mixin class
        botmovementenabled = !botmovementenabled;
    }
    public static void toggleAutoFarmButton() {
        // call the toggleFlight method of the TutorialMod Mixin class
        autofarmenabled = !autofarmenabled;
    }

    public void tick(ClientPlayerEntity player){
        if(player != null){
            for (int y = -1; y <= 0; y++){
                for (int x = -2; x <= 2; x++){
                    for (int z = -2; z <= 2; z++){
                        tryPlant(player,player.getBlockPos().add(x,y,z),Hand.MAIN_HAND);
                    }
                    }
                }
            }
        }
    public void tryPlant(ClientPlayerEntity player, BlockPos block, Hand hand){
        BlockState blockstate = player.world.getBlockState(block);
        if(player.world.getBlockState(block).getBlock().asItem().toString() == "farmland" && checkItemInHand(player) == "true"){
            Vec3d blockPos = new Vec3d(block.getX(),block.getY(),block.getZ());
            BlockHitResult hit = new BlockHitResult(blockPos, Direction.UP,block,false);
            MinecraftClient.getInstance().interactionManager.interactBlock(player,hand,hit);
            TutorialModClient.logInfo(player.interactAt(player,Vec3d.ofCenter(block),player.getActiveHand()).toString());
            player.interactAt(player,Vec3d.ofCenter(block),player.getActiveHand());
        }
    }
    public String checkItemInHand(ClientPlayerEntity player){
        Item active_item = player.getMainHandStack().getItem();
        TutorialModClient.logInfo(active_item.toString());
        if(active_item.toString().equals("wheat_seeds") || active_item.toString().equals("potato") || active_item.toString().equals("carrot")){
            return "true";
        }
        return "false";
    }
}
//make it so that i can look which item i am holding

