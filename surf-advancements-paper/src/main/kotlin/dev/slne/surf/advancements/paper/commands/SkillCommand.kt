package dev.slne.surf.advancements.paper.commands

import com.github.shynixn.mccoroutine.folia.globalRegionDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.jorel.commandapi.arguments.AsyncPlayerProfileArgument
import dev.jorel.commandapi.kotlindsl.argument
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.paper.command.executors.playerExecutorSuspend
import dev.slne.surf.api.paper.command.util.awaitAsyncPlayerProfile
import dev.slne.surf.api.paper.command.util.idOrThrow
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.advancements.api.paper.player.AdvancementPlayerManager
import dev.slne.surf.advancements.paper.menu.advancementsView
import dev.slne.surf.advancements.paper.plugin
import dev.slne.surf.advancements.paper.utils.AdvancementPermissions
import kotlinx.coroutines.withContext
import org.bukkit.Bukkit

fun advancementCommand() = commandTree("advancement") {
    withPermission(AdvancementPermissions.COMMAND_ADVANCEMENT)

    playerExecutor { player, _ ->
        plugin.launch {
            val advancementPlayer = AdvancementPlayerManager.fetchOrCreatePlayer(player.uniqueId)

            withContext(plugin.globalRegionDispatcher) {
                advancementsView.open(
                    player, mapOf(
                        "advancement_progress" to advancementPlayer.experiences,
                        "player_uuid" to player.uniqueId
                    )
                )
            }
        }
    }

    argument(AsyncPlayerProfileArgument("target")) {
        withPermission(AdvancementPermissions.COMMAND_ADVANCEMENT_OTHER)
        playerExecutorSuspend { player, arguments ->
            val target =
                Bukkit.getOfflinePlayer(arguments.awaitAsyncPlayerProfile("target").idOrThrow())

            if (!target.hasPlayedBefore()) {
                player.sendText {
                    appendErrorPrefix()
                    error("Der Spieler hat noch nie auf diesem Server gespielt.")
                }
                return@playerExecutorSuspend
            }

            val advancementPlayer = AdvancementPlayerManager.fetchOrCreatePlayer(target.uniqueId)

            withContext(plugin.globalRegionDispatcher) {
                advancementsView.open(
                    player, mapOf(
                        "advancement_progress" to advancementPlayer.experiences,
                        "player_uuid" to target.uniqueId
                    )
                )
            }
        }
    }
}