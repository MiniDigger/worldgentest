package me.minidigger.worldgentest;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;
import java.util.UUID;

public class TestBlockPopulator extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk source) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    Block block = source.getBlock(x, y, z);
                    switch (block.getType()) {
                        case SIGN_POST:
                            Sign sign = (Sign) block.getState();
                            sign.setLine(1, "Hello World!");
                            sign.update();
                            break;
                        case SKULL:
                            Skull skull = (Skull) block.getState();
                            skull.setSkullType(SkullType.PLAYER);
                            skull.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString("2c771d16-f492-4f7a-adb7-61c38a9c9bfb")));// MiniDigger
                            skull.update();
                            break;
                    }
                }
            }
        }
    }
}
