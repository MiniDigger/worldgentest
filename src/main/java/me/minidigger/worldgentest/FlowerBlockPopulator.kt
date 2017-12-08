package me.minidigger.worldgentest

import org.bukkit.Chunk
import org.bukkit.GrassSpecies
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.BlockFace
import org.bukkit.generator.BlockPopulator
import org.bukkit.material.LongGrass
import java.util.*

class FlowerBlockPopulator : BlockPopulator() {

    override fun populate(world: World, random: Random, chunk: Chunk) {
        for (x in 0..15) {
            for (z in 0..15) {
                val y = world.getHighestBlockYAt(chunk.x shl 4, chunk.z shl 4)

                val block = chunk.getBlock(x, y, z)
                if (block.type == Material.AIR && block.getRelative(BlockFace.DOWN).type == Material.GRASS) {
                    val n = random.nextInt(64)
                    when {
                        n < 1 -> block.type = Material.RED_ROSE
                        n < 4 -> block.type = Material.YELLOW_FLOWER
                        n < 16 -> {
                            block.type = Material.LONG_GRASS
                            block.state.apply {
                                data = LongGrass().apply {
                                    species = GrassSpecies.NORMAL
                                }
                                update()
                            }// why do I need a state here?.....
                        }
                        n < 32 -> {
                            block.type = Material.DOUBLE_PLANT
                            block.getRelative(BlockFace.UP).type = Material.DOUBLE_PLANT
                            // no material data for double plant.......
                        }
                    }
                }
            }
        }
    }
}
