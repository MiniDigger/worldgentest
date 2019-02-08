package me.minidigger.worldgentest.populators

import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.BlockFace
import org.bukkit.generator.BlockPopulator
import java.util.*


class Lake2Populator : BlockPopulator() {

    override fun populate(world: World, random: Random, chunk: Chunk) {
        for (x in 0..15) {
            for (z in 0..15) {
                val y = world.getHighestBlockYAt(chunk.x shl 4, chunk.z shl 4) + 1
                if(y == 2){
                    val block = chunk.getBlock(x, y, z)
                    if(block.type == Material.AIR){
                        block.type = Material.STATIONARY_WATER
                        block.getRelative(BlockFace.DOWN).type = Material.SAND
                    }
                }
            }
        }
    }
}