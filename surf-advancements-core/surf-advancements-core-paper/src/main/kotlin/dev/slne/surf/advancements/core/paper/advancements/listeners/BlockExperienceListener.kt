package dev.slne.surf.advancements.core.paper.advancements.listeners

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.advancements.api.paper.advancements.ExampleAdvancement
import dev.slne.surf.advancements.api.paper.player.advancementPlayer
import dev.slne.surf.advancements.api.paper.player.incrementExperience
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

object BlockExperienceListener : Listener {

    /**
     * XP je Block-Typ.
     * Blöcke, die nicht in der Map stehen, geben 0 XP.
     */
    private val xpTable = mapOf(
        Material.STONE          to 1,
        Material.COBBLESTONE    to 1,
        Material.DEEPSLATE      to 2,
        Material.ANDESITE       to 1,
        Material.DIORITE        to 1,
        Material.GRANITE        to 1,
        Material.COAL_ORE       to 3,
        Material.DEEPSLATE_COAL_ORE to 4,
        Material.IRON_ORE       to 5,
        Material.DEEPSLATE_IRON_ORE to 6,
        Material.COPPER_ORE     to 4,
        Material.DEEPSLATE_COPPER_ORE to 5,
        Material.GOLD_ORE       to 7,
        Material.DEEPSLATE_GOLD_ORE to 8,
        Material.REDSTONE_ORE   to 6,
        Material.DEEPSLATE_REDSTONE_ORE to 7,
        Material.LAPIS_ORE      to 8,
        Material.DEEPSLATE_LAPIS_ORE to 9,
        Material.DIAMOND_ORE    to 15,
        Material.DEEPSLATE_DIAMOND_ORE to 18,
        Material.EMERALD_ORE    to 20,
        Material.DEEPSLATE_EMERALD_ORE to 24,
        Material.NETHER_QUARTZ_ORE to 5,
        Material.NETHER_GOLD_ORE to 7,
        Material.ANCIENT_DEBRIS to 50,
        Material.OBSIDIAN       to 10,
        Material.CRYING_OBSIDIAN to 12,
        Material.NETHERRACK    to 1,
        Material.BASALT        to 1,
        Material.BLACKSTONE    to 2,
        Material.END_STONE     to 3,
    )

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val xp = xpTable[event.block.type] ?: return
        val player = event.player
        val plugin = JavaPlugin.getProvidingPlugin(BlockExperienceListener::class.java)

        plugin.launch {
            player.advancementPlayer().incrementExperience<ExampleAdvancement>(xp)
        }
    }
}

