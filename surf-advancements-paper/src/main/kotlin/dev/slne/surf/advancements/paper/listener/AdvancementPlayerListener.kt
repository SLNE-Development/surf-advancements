package dev.slne.surf.advancements.paper.listener

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import dev.slne.surf.advancements.paper.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object AdvancementPlayerListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        plugin.launch {
            AdvancementProgressManager.fetchProgress(event.player.uniqueId).forEach {
                AdvancementProgressManager.cacheProgress(it)
            }
        }
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        plugin.launch {
            AdvancementProgressManager.flushPlayerQueue(event.player.uniqueId)
        }
    }
}