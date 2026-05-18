package dev.slne.surf.advancements.paper

import com.github.shynixn.mccoroutine.folia.scope
import com.google.auto.service.AutoService
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import dev.slne.surf.advancements.core.paper.PaperLoader
import net.kyori.adventure.util.Services

@AutoService(PaperAdvancementInstance::class)
class PaperAdvancementInstanceImpl : PaperAdvancementInstance, Services.Fallback {
    override val paperLoader = PaperLoader(plugin.dataPath, plugin.scope)
}