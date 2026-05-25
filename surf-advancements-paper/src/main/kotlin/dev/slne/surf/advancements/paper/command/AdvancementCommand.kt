package dev.slne.surf.advancements.paper.command

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.advancements.paper.menu.advancementsView
import dev.slne.surf.advancements.paper.plugin
import kotlinx.coroutines.withContext

fun advancementCommand() = commandTree("advancements") {
    playerExecutor { player, _ ->
        plugin.launch {
            withContext(plugin.entityDispatcher(player)) {
                advancementsView.open(
                    player,
                    mapOf("player_uuid" to player.uniqueId)
                )
            }
        }
    }
}
