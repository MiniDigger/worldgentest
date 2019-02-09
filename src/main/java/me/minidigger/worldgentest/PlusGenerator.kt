package me.minidigger.worldgentest

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

class PlusGenerator(id: String) : ChunkGenerator() {

    private val size = id.split("-")[1].toInt()

    override fun generateChunkData(world: World, random: Random?, x: Int, z: Int, biome: ChunkGenerator.BiomeGrid): ChunkGenerator.ChunkData {
        return if (shouldFillChunk(x, z)) {
            // main content

            val chunkData = createVanillaChunkData(world, x , z)

            // borders
            if (!shouldFillChunk(x + 1, z)) chunkData.setRegion(15, 0, 0, 16, 256, 16, Material.BEDROCK)
            if (!shouldFillChunk(x, z + 1)) chunkData.setRegion(0, 0, 15, 16, 256, 16, Material.BEDROCK)
            if (!shouldFillChunk(x - 1, z)) chunkData.setRegion(0, 0, 0, 1, 256, 16, Material.BEDROCK)
            if (!shouldFillChunk(x, z - 1)) chunkData.setRegion(0, 0, 0, 16, 256, 1, Material.BEDROCK)

            chunkData
        } else {
            createChunkData(world) // return empty
        }
    }

    override fun canSpawn(world: World, x: Int, z: Int) = shouldFillChunk(x, z)

    override fun getFixedSpawnLocation(world: World?, random: Random?) = Location(world, 0.0, 64.0, 0.0)

    private fun shouldFillChunk(tx: Int, tz: Int): Boolean {
        val x = if (tx < 0) -tx else tx + 1
        val z = if (tz < 0) -tz else tz + 1

        return (x < size && z < size * 2) || (x < size * 2 && z < size)
    }

    override fun shouldAddVanillaDecorations() = true

    override fun shouldAddVanillaFeatures() = true

    override fun shouldAddVanillaMobs() = true
}