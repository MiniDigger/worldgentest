package me.minidigger.worldgentest

import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin

class Worldgentest : JavaPlugin() {

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator? = when {
        id == "test" -> TestChunkGenerator()
        id == "hill" -> HillChunkGenerator()
        id == "light" -> LightTestGenerator()
        id!!.startsWith("plus") -> PlusGenerator(id)
        else -> super.getDefaultWorldGenerator(worldName, id)
    }
}
