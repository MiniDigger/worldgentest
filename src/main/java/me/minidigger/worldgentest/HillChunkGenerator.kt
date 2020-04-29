package me.minidigger.worldgentest

import me.minidigger.worldgentest.populators.FlowerBlockPopulator
import me.minidigger.worldgentest.populators.Lake2Populator
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.generator.ChunkGenerator
import org.bukkit.util.noise.PerlinOctaveGenerator
import java.util.*
import kotlin.math.roundToInt

class HillChunkGenerator : ChunkGenerator() {

    private val baseHeight = 32
    private val hillAmplifier = 12
    private val amplitude = 1.0
    private val frequency = 1.0
    private val octaves = 20
    private val scale = 1 / 128.0

    override fun getFixedSpawnLocation(world: World, random: Random) = Location(world, 4.0, 4.0, 4.0)

    override fun generateChunkData(world: World, random: Random, chunkX: Int, chunkZ: Int, biome: ChunkGenerator.BiomeGrid): ChunkGenerator.ChunkData {
        val chunkData = createChunkData(world)

        val perlin = PerlinOctaveGenerator(world, octaves)
        perlin.setScale(scale)

        for (x in 0..15) {
            for (z in 0..15) {
                biome.setBiome(x, z, Biome.PLAINS)
                chunkData.setBlock(x, 0, z, Material.BEDROCK)

                val height: Int = (baseHeight + perlin.noise(x + chunkX * 16.0, z + chunkZ * 16.0, frequency, amplitude) * hillAmplifier).roundToInt()
                chunkData.setRegion(x, 1, z, x + 1, height, z + 1, Material.STONE)
                chunkData.setRegion(x, height, z, x + 1, height + 1, z + 1, Material.GRASS)
            }
        }

        return chunkData
    }

    override fun canSpawn(world: World, x: Int, z: Int) = true

    override fun getDefaultPopulators(world: World) = listOf(FlowerBlockPopulator(), Lake2Populator())
}
