package me.minidigger.worldgentest

import me.minidigger.worldgentest.populators.TestBlockPopulator
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import org.bukkit.material.*
import java.util.*

class TestChunkGenerator : ChunkGenerator() {

    private val layers = ArrayList<MaterialData>()

    init {
        layers.add(MaterialData(Material.BEDROCK))
        layers.add(Wool(DyeColor.RED))
        layers.add(Wood(TreeSpecies.ACACIA))
        layers.add(Tree(TreeSpecies.BIRCH))
        layers.add(Leaves(TreeSpecies.REDWOOD))
        layers.add(Stairs(Material.BRICK_STAIRS).apply {
            isInverted = true
            setFacingDirection(BlockFace.EAST)
        })
        layers.add(Skull(BlockFace.EAST_NORTH_EAST))
        layers.add(Sandstone(SandstoneType.GLYPHED))
        layers.add(Sign().apply { setFacingDirection(BlockFace.SOUTH_SOUTH_EAST) })
    }

    override fun generateChunkData(world: World?, random: Random?, x: Int, z: Int, biome: ChunkGenerator.BiomeGrid?): ChunkGenerator.ChunkData {
        val chunkData = createChunkData(world)

        layers.withIndex().forEach { (layer, data) -> chunkData.setRegion(layer, layer, layer, 16 - layer, layer + 1, 16 - layer, data) }

        val data = layers[8]
        chunkData.setRegion(7, 8, 7, 9, 9, 9, data)

        return chunkData
    }

    override fun canSpawn(world: World, x: Int, z: Int) = true

    override fun getDefaultPopulators(world: World?) = listOf<BlockPopulator>(TestBlockPopulator())

    override fun getFixedSpawnLocation(world: World?, random: Random?) = Location(world, 4.0, 4.0, 4.0)
}