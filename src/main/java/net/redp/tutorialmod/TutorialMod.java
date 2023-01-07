package net.redp.tutorialmod;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.redp.tutorialmod.mixin.ClientConnectionInvoker;

import java.util.logging.Logger;


public class TutorialMod implements ModInitializer {

    public static final String MOD_ID = "TutorialMod";

    public static final String Mod_NAME = "TutorialMod";
    private static final Logger LOGGER = null;

    public static boolean flightenabled = false;
    public static boolean botmovementenabled = false;
    public static boolean autofarmenabled = false;

    public static boolean togglexzcheck = false;


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
                    //setPlayerPosition(player,);
                    movement(player);
                    //checkxz(player);
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
    public static void sendPacket(Vec3d pos){
        ClientPlayerEntity client = MinecraftClient.getInstance().player;
        ClientConnectionInvoker conn =(ClientConnectionInvoker)client.networkHandler.getConnection();
        TutorialModClient.logInfo(conn.toString());
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y, pos.z, true);
        conn.sendIm(packet, null);
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
       }else{
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
    public static void writeBuff(PlayerEntity player,Vec3d pos){
        Packet packet = new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y, pos.z, true);
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());

// Write the player's position to the packetByteBuf
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;
        packetByteBuf.writeDouble(x);
        packetByteBuf.writeDouble(y);
        packetByteBuf.writeDouble(z);

// Write the player's look angles to the packetByteBuf

// Write the "onGround" field to the packetByteBuf
        boolean onGround = true;
        packetByteBuf.writeBoolean(onGround);
        //ServerPlayNetworking.send(PacketType.Server.CUSTOM_PAYLOAD, packetByteBuf, player.getServer(), NetworkDirection.PLAY_TO_SERVER);
        //ServerPlayNetworking.send(ServerPlayerEntity player,1,packetByteBuf);
        Identifier identifier = new Identifier("1");
        ClientPlayNetworking.send(identifier,packetByteBuf);
    }
    public static void movement(PlayerEntity player){
        String direction = MinecraftClient.getInstance().player.getHorizontalFacing().toString();
        Vec3d pos = new Vec3d(player.getX(),player.getY(),player.getZ());
        switch(direction) {
            case "west":
                // code block
                TutorialModClient.logInfo(pos.toString());
                TutorialModClient.logInfo(pos.add(0.1d,0.0d,0.0d).toString());
                //player.handler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(pos.x, pos.y + 0.0625, pos.z, true));
                //sendPacket(pos.add(0.1d,0.0d,0.0d));
                TutorialMod.setPlayerPosition(player,pos.add(1.0d,0.0d,0.0d));

                break;
            case "east":
                sendPacket(pos.subtract(0.1d,0.0d,0.0d));
                break;
            case "south":
                sendPacket(pos.subtract(0.0d,0.0d,0.1d));
                break;
            case "north":
                sendPacket(pos.add(0.0d,0.0d,0.1d));
                break;
        }
    }

}
//make it so that i can look which item i am holding

