package dev.slne.surf.advancements.paper.listener

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.advancements.core.paper.manager.AdvancementManager
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
            AdvancementProgressManager.fetchProgress(event.player.uniqueId).forEach { progress ->
                val advancement = AdvancementManager.getAdvancement(progress.advancementId)
                val filledProgress = if (advancement != null) {
                    progress.copy(requiredProgress = advancement.requiredProgress)
                } else {
                    progress
                }
                AdvancementProgressManager.cacheProgress(filledProgress)
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
