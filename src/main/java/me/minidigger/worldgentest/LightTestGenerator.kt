package me.minidigger.worldgentest

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

class LightTestGenerator : ChunkGenerator() {

    override fun getFixedSpawnLocation(world: World, random: Random) = Location(world, 4.0, 4.0, 4.0)

    override fun generateChunkData(world: World, random: Random, chunkX: Int, chunkZ: Int, biome: ChunkGenerator.BiomeGrid): ChunkGenerator.ChunkData {
        val chunkData = createChunkData(world)

        chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.STONE)
        chunkData.setRegion(0, 15, 0, 16, 16, 16, Material.STONE)

        chunkData.setBlock(8, 8, 8, Material.SEA_LANTERN)

        return chunkData
    }

    override fun canSpawn(world: World, x: Int, z: Int) = true
}
