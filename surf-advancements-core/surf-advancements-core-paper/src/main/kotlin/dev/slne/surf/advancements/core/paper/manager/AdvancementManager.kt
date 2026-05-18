package dev.slne.surf.advancements.core.paper.manager

import dev.slne.surf.advancements.api.Advancement
import dev.slne.surf.advancements.api.AdvancementCategory
import dev.slne.surf.advancements.core.paper.PaperAdvancement
import dev.slne.surf.api.paper.event.register
import io.ktor.util.collections.*
import net.kyori.adventure.key.Key
import org.bukkit.event.Listener

object AdvancementManager {
    private val _advancements = ConcurrentMap<Key, Advancement>()
    private val _categories = ConcurrentMap<Key, AdvancementCategory>()

    fun getAdvancement(id: Key): Advancement? = _advancements[id]
    fun getCategory(id: Key): AdvancementCategory? = _categories[id]

    val advancements get() = _advancements.values
    val categories get() = _categories.values

    fun registerCategory(category: AdvancementCategory) {
        _categories[category.id] = category
        category.members.forEach { registerAdvancement(it) }
    }

    fun registerAdvancement(advancement: Advancement) {
        _advancements[advancement.id] = advancement

        if (advancement is PaperAdvancement) {
            advancement.listeners.forEach(Listener::register)
        }
    }


    fun create() {
        
    }
}