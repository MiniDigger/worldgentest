package me.minidigger.worldgentest.populators

import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.SkullType
import org.bukkit.World
import org.bukkit.block.Sign
import org.bukkit.block.Skull
import org.bukkit.generator.BlockPopulator
import java.util.*

class TestBlockPopulator : BlockPopulator() {
    override fun populate(world: World, random: Random, source: Chunk) {
        for (x in 0..15) {
            for (y in 0..15) {
                for (z in 0..15) {
                    val block = source.getBlock(x, y, z)
                    when (block.state) {
                        is Sign -> {
                            val sign = block.state as Sign
                            sign.setLine(1, "Hello World!")
                            sign.update()
                        }
                        is Skull -> {
                            val skull = block.state as Skull
                            skull.skullType = SkullType.PLAYER
                            skull.owningPlayer = Bukkit.getOfflinePlayer(UUID.fromString("2c771d16-f492-4f7a-adb7-61c38a9c9bfb"))// MiniDigger
                            skull.update()
                        }
                    }
                }
            }
        }
    }
}
