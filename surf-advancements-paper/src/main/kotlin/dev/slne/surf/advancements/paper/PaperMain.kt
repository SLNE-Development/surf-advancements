package dev.slne.surf.advancements.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.advancements.api.paper.player.AdvancementPlayerManager
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import dev.slne.surf.advancements.core.paper.manager.advancementManagerImpl
import dev.slne.surf.advancements.paper.commands.advancementCommand
import dev.slne.surf.advancements.paper.listener.ListenerManager
import dev.slne.surf.advancements.paper.menu.advancementView
import dev.slne.surf.advancements.paper.menu.advancementsView
import org.bukkit.plugin.java.JavaPlugin

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        PaperAdvancementInstance.paperLoader.onLoad()

        advancementsView.register()
        advancementView.register()
    }

    override suspend fun onEnableAsync() {
        advancementCommand()

        advancementManagerImpl.registerAllAdvancements()
        ListenerManager.register()
    }

    override suspend fun onDisableAsync() {
        server.onlinePlayers.forEach { player ->
            val uuid = player.uniqueId

            AdvancementPlayerManager.savePlayer(uuid)
            AdvancementPlayerManager.invalidatePlayer(uuid)
        }

        PaperAdvancementInstance.paperLoader.onDisable()
    }
}

val plugin = JavaPlugin.getPlugin(PaperMain::class.java)