package dev.slne.surf.advancements.core.paper.manager

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObjectSetOf
import dev.slne.surf.advancements.api.common.InternalAdvancementApi
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.manager.AdvancementManager
import dev.slne.surf.advancements.api.paper.advancements.*import net.kyori.adventure.util.Services
import kotlin.reflect.KClass

@AutoService(AdvancementManager::class)
@OptIn(InternalAdvancementApi::class)
class AdvancementManagerImpl : AdvancementManager, Services.Fallback {
    private val _advancements = mutableObjectSetOf<Advancement>()
    override val advancements get() = _advancements.freeze()

    fun registerAllAdvancements() {
        registerAdvancement(PlaytimeAdvancement)
        registerAdvancement(MiningAdvancement)
    }

    fun registerListeners() {
        advancements.filter { it.active }.forEach { it.registerListeners() }
    }

    override fun registerAdvancement(advancement: Advancement): Boolean {
        return _advancements.add(advancement)
    }

    override fun unregisterAdvancement(advancement: Advancement): Boolean {
        return _advancements.remove(advancement)
    }

    override fun getAdvancementByName(name: String) =
        _advancements.firstOrNull { it.name.equals(name, true) }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Advancement> getAdvancement(advancementClazz: KClass<out T>) =
        _advancements.firstOrNull { advancementClazz.isInstance(it) } as? T
}

val advancementManagerImpl get() = AdvancementManager.INSTANCE as AdvancementManagerImpl