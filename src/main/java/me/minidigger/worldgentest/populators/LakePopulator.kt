package me.minidigger.worldgentest.populators

import org.bukkit.Chunk
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.generator.BlockPopulator
import org.bukkit.util.BlockVector
import org.bukkit.util.Vector
import java.util.*


class LakePopulator : BlockPopulator() {

    override fun populate(world: World, random: Random, chunk: Chunk) {
        if (random.nextInt(10) > 1) {
            return
        }

        val rx = (chunk.x shl 4) + random.nextInt(16)
        val rz = (chunk.z shl 4) + random.nextInt(16)
        if (world.getHighestBlockYAt(rx, rz) <= 4) return

        var ry = 6 + random.nextInt(world.getHighestBlockYAt(rx, rz) - 3)
        val radius = 2 + random.nextInt(3)

        var liquidMaterial = Material.LAVA
        var solidMaterial = Material.OBSIDIAN

        if (random.nextInt(10) < 3) {
            ry = world.getHighestBlockYAt(rx, rz) - 1
        }

        if (random.nextInt(96) < ry) {
            liquidMaterial = Material.WATER
            solidMaterial = Material.STONE
        }

        val lakeBlocks = ArrayList<Block>()
        for (i in -1..3) {
            val center = BlockVector(rx, ry - i, rz)
            for (x in -radius..radius) {
                (-radius..radius)
                        .map { center.clone().add(Vector(x, 0, it)) }
                        .filter { center.distance(it) <= radius + 0.5 - i }
                        .mapTo(lakeBlocks) { world.getBlockAt(it.toLocation(world)) }
            }
        }

        lakeBlocks.forEach { block ->
            if (block.y == ry + 1) {
                if (random.nextBoolean()) {
                    block.type = Material.AIR
                }
            } else if (block.y == ry) {
                block.type = Material.AIR
            } else if (random.nextInt(10) > 1) {
                block.type = liquidMaterial
            } else {
                block.type = solidMaterial
            }
        }
    }
}