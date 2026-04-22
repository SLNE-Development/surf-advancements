package dev.slne.surf.advancements.api.paper.manager

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.advancements.api.paper.Advancement
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.jetbrains.annotations.Unmodifiable
import kotlin.reflect.KClass

private val advancementManager = requiredService<AdvancementManager>()

interface AdvancementManager {
    val advancements: @Unmodifiable ObjectSet<Advancement>

    fun registerAdvancement(advancement: Advancement): Boolean
    fun unregisterAdvancement(advancement: Advancement): Boolean

    fun getAdvancementByName(name: String): Advancement?
    fun <T : Advancement> getAdvancement(advancementClazz: KClass<out T>): T?

    companion object : AdvancementManager by advancementManager {
        val INSTANCE get() = advancementManager
    }
}

inline fun <reified T : Advancement> AdvancementManager.getAdvancement() = getAdvancement(T::class)