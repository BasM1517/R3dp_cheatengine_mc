package net.redp.tutorialmod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TutorialMod implements ModInitializer {

    public static final Item FLIGHT_ITEM = new Item(new Item.Settings().group(ItemGroup.MISC));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("tutorialmod", "flight_item"), FLIGHT_ITEM);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            World world = client.world;
            PlayerEntity player = client.player;
            if (player != null && world != null && player.isSneaking()) {
                player.addVelocity(0, 0.1, 0);
            }
        });
    }
}