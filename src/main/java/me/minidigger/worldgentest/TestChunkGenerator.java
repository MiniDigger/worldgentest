package me.minidigger.worldgentest;

import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestChunkGenerator extends ChunkGenerator {

    private List<MaterialData> layers = new ArrayList<>();

    public TestChunkGenerator() {
        layers.add(new MaterialData(Material.BEDROCK));
        layers.add(new Wool(DyeColor.RED));
        layers.add(new Wood(TreeSpecies.ACACIA));
        layers.add(new Tree(TreeSpecies.BIRCH));
        layers.add(new Leaves(TreeSpecies.REDWOOD));
        Stairs stairs = new Stairs(Material.BRICK_STAIRS);
        stairs.setInverted(true);
        stairs.setFacingDirection(BlockFace.EAST);
        layers.add(stairs);
        Skull skull = new Skull();
        skull.setFacingDirection(BlockFace.EAST_NORTH_EAST);
        //skull.setOwningPlayer("MiniDigger");
        layers.add(skull);
        layers.add(new Sandstone(SandstoneType.GLYPHED));
        Sign sign = new Sign();
        sign.setFacingDirection(BlockFace.SOUTH_SOUTH_WEST);
        //sign.setLines();
        layers.add(sign);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);

        for (int layer = 0; layer < 8 && layer < layers.size(); layer++) {
            MaterialData data = layers.get(layer);
            chunkData.setRegion(layer, layer, layer, 16 - layer, layer + 1, 16 - layer, data);
        }

        MaterialData data = layers.get(8);
        chunkData.setRegion(7, 8, 7, 9, 9, 9, data);

        return chunkData;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.singletonList(new TestBlockPopulator());
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 4, 4, 4);
    }
}
