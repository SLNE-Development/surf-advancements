package dev.slne.surf.advancements.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import dev.slne.surf.advancements.core.paper.manager.AdvancementManager
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import dev.slne.surf.advancements.paper.listener.AdvancementPlayerListener
import dev.slne.surf.api.paper.event.register
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onLoadAsync() {
        PaperAdvancementInstance.paperLoader.onLoad()
    }

    override suspend fun onEnableAsync() {
        AdvancementManager.create()
        AdvancementPlayerListener.register()

        Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
            plugin.launch {
                AdvancementProgressManager.flushProgressQueue()
            }
        }, 1, 1, TimeUnit.MINUTES)
    }

    override suspend fun onDisableAsync() {
        AdvancementProgressManager.flushProgressQueue()
        PaperAdvancementInstance.paperLoader.onDisable()
    }
}