package dev.slne.surf.advancements.core.paper

import dev.slne.surf.advancements.api.paper.AdvancementInstance

interface PaperAdvancementInstance : AdvancementInstance {
    val paperLoader: PaperLoader
    val rabbitApi get() = paperLoader.rabbitApi
    
    companion object : PaperAdvancementInstance by AdvancementInstance.INSTANCE as PaperAdvancementInstance {
        val INSTANCE get() = AdvancementInstance.INSTANCE as PaperAdvancementInstance
    }
}