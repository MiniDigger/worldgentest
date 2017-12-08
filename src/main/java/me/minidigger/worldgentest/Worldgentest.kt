package me.minidigger.worldgentest

import org.bukkit.plugin.java.JavaPlugin

class Worldgentest : JavaPlugin() {

    override fun getDefaultWorldGenerator(worldName: String?, id: String?) = TestChunkGenerator()
}