package dev.slne.surf.advancements.paper.listener

import dev.slne.surf.api.paper.event.register
import dev.slne.surf.advancements.core.paper.manager.advancementManagerImpl
import dev.slne.surf.advancements.core.paper.advancements.listeners.BlockExperienceListener

object ListenerManager {
    fun register() {
        advancementManagerImpl.registerListeners()
        ExperienceServiceListener.register()
        BlockExperienceListener.register()
    }
}