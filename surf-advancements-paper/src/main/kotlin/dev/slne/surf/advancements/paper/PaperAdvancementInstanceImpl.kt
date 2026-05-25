package dev.slne.surf.advancements.paper

import com.google.auto.service.AutoService
import dev.slne.surf.advancements.core.common.AdvancementInstance
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import dev.slne.surf.advancements.core.paper.PaperLoader
import net.kyori.adventure.util.Services

@AutoService(AdvancementInstance::class)
class PaperAdvancementInstanceImpl : PaperAdvancementInstance, Services.Fallback {
    override val paperLoader = PaperLoader(plugin.dataPath)
}