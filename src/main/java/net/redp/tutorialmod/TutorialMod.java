package net.redp.tutorialmod;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
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

    public static boolean togglexzcheck = false;

    public static boolean cordsmanipulate = true;

    public static PacketByteBuf buf;


    @Override
    public void onInitialize() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;

            if (player != null){
                player.getAbilities().flying = flightenabled;
                if (flightenabled){

                    //sendPacket(player.getPos().subtract((Math.abs(player.getX()) % 10),0,Math.abs(player.getZ()) % 10));
                    player.setVelocity(player.getVelocity().x,player.getVelocity().y - 0.04,player.getVelocity().z);
                    //checkItemInHand(player);
                }
                if(autofarmenabled){
                    tick(player);
                }
                if(togglexzcheck){
                    //setPlayerMovement(player);
                    player.setPos(Math.round(player.getX()),player.getY(),Math.round(player.getZ()));
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
    public static void toggleXZCheckButton() {
        // call the toggleFlight method of the TutorialMod Mixin class
        togglexzcheck = !togglexzcheck;
    }
    public static void toggleAutoFarmButton() {
        // call the toggleFlight method of the TutorialMod Mixin class
        autofarmenabled = !autofarmenabled;
    }
    public static double getx() {
        // call the toggleFlight method of the TutorialMod Mixin class
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        return player.getX();
    }
    public static double getz() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        return player.getZ();
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
    public static void checkxz(PlayerEntity player){
       double dx = player.getX();
       double dz = player.getZ();
       long x = ((long )(dx * 1000) % 10);
       long z = ((long )(dz * 1000) % 10);
       if(x == 0 && z == 0){
           TutorialModClient.logInfo("you're cords are 0");

           //TutorialModClient.logInfo(String.valueOf(player.getX()));

           TutorialModClient.logInfo("x : " + String.valueOf(x) + " z : " + String.valueOf(z));
       }else {
           TutorialModClient.logInfo("x : " + String.valueOf(x) + " z : " + String.valueOf(z));
       }
    }
    public static void setPlayerPosition(PlayerEntity player, Vec3d newPosition){
        TutorialModClient.logInfo(String.valueOf((newPosition.getX() * 1000) % 10) + " " + String.valueOf((newPosition.getZ() * 1000) % 10 + newPosition.getX() % 10 == 0.0 && newPosition.getZ() % 10 == 0.0));
        player.setPosition(newPosition.getX(),player.getY(),newPosition.getZ());
        if (newPosition.getX() % 10 == 0.0 && newPosition.getZ() % 10 == 0.0) {
            TutorialModClient.logInfo(newPosition.toString() + "in ieder geval een succes");

        }
    }
    public static PacketByteBuf writeBuffExample(PacketByteBuf buf){
        ClientPlayerEntity client = MinecraftClient.getInstance().player;
        double xPlayer = Math.round(client.getX());
        double zPlayer = Math.round(client.getZ());

        buf.clear();
        buf.writeDouble(xPlayer);
        buf.writeDouble(client.getY());
        buf.writeDouble(zPlayer);
        buf.getUnsignedByte(true ? 1 : 0);
        return buf;
    }
    public static void movement(PlayerEntity player){
    }
    public void calculatenewposition(PlayerEntity player){

    }
    public void setPlayerMovement(ClientPlayerEntity player) {
        // Round the movement to the nearest hundredth
        Vec3d currentPosition = player.getPos();
        Vec3d targetPosition = new Vec3d(10, 0, 10);
        Vec3d newMovement = targetPosition.subtract(currentPosition).normalize();
        double x = Math.round(newMovement.getX() * 100.0) / 100.0;
        double y = Math.round(newMovement.getY() * 100.0) / 100.0;
        double z = Math.round(newMovement.getZ() * 100.0) / 100.0;
        player.setVelocity(x, y, z);
    }}
//make it so that i can look which item i am holding

