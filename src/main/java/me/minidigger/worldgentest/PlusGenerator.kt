package me.minidigger.worldgentest

import com.sun.org.apache.xpath.internal.operations.Plus
import net.minecraft.server.v1_13_R2.*
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld
import org.bukkit.generator.ChunkGenerator
import java.util.*

class PlusGenerator(id: String) : ChunkGenerator() {

    private val size = id.split("-")[1].toInt()

    private lateinit var overworldGen : ChunkProviderGenerate

    fun getNmsGen(world : World): ChunkProviderGenerate {
        val nmsWorld = (world as CraftWorld).handle
        val genSettings = GeneratorSettingsOverworld()
        val biomeProvider = WorldChunkManagerOverworld(BiomeLayoutOverworldConfiguration())
        // todo looks like we need to set worlddata before being able to use biome layout overworld config
        val overworldGen = ChunkProviderGenerate(nmsWorld, biomeProvider, genSettings)
        return overworldGen
    }

    override fun generateChunkData(world: World, random: Random?, x: Int, z: Int, biome: ChunkGenerator.BiomeGrid): ChunkGenerator.ChunkData {
        val chunkData = createChunkData(world)


        if (shouldFillChunk(x, z)) {
            // main content //TODO delegate to vanilla chunk gen
//            chunkData.setRegion(0, 63, 0, 16, 64, 16, Material.GRASS_BLOCK)
//            chunkData.setRegion(0, 1, 0, 16, 63, 16, Material.DIRT)
//            chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.BEDROCK)

            if(!::overworldGen.isInitialized) {
                overworldGen = getNmsGen(world)
            }

            val chunkPrimer = ProtoChunk(ChunkCoordIntPair(x, z), ChunkConverter.a) // empty chunk converter
            overworldGen.a(x, z, chunkPrimer)

            // borders
            when {
                !shouldFillChunk(x + 1, z) -> chunkData.setRegion(15, 0, 0, 16, 256, 16, Material.BEDROCK)
                !shouldFillChunk(x, z + 1) -> chunkData.setRegion(0, 0, 15, 16, 256, 16, Material.BEDROCK)
                !shouldFillChunk(x - 1, z) -> chunkData.setRegion(0, 0, 0, 1, 256, 16, Material.BEDROCK)
                !shouldFillChunk(x, z - 1) -> chunkData.setRegion(0, 0, 0, 16, 256, 1, Material.BEDROCK)
            }
        }

        return chunkData
    }

    override fun canSpawn(world: World, x: Int, z: Int) = shouldFillChunk(x, z)

    override fun getFixedSpawnLocation(world: World?, random: Random?) = Location(world, 0.0, 64.0, 0.0)

    private fun shouldFillChunk(tx: Int, tz: Int): Boolean {
        val x = if (tx < 0) -tx else tx + 1
        val z = if (tz < 0) -tz else tz + 1

        return (x < size && z < size * 2) || (x < size * 2 && z < size)
    }
}