package dev.slne.surf.advancements.api.paper

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import org.bukkit.Location
import org.bukkit.entity.Entity
import java.util.*
import kotlin.coroutines.CoroutineContext

private val instance = requiredService<AdvancementInstance>()

interface AdvancementInstance {
    fun launch(
        context: CoroutineContext = mainDispatcher,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job

    val globalRegionDispatcher: CoroutineContext
    val mainDispatcher: CoroutineContext
    val asyncDispatcher: CoroutineContext
    fun entityDispatcher(entity: Entity): CoroutineContext
    fun regionDispatcher(location: Location): CoroutineContext

    fun createAdvancementExperience(
        uuid: UUID,
        advancement: Advancement,
        currentExperience: Int
    ): AdvancementExperience

    companion object : AdvancementInstance by instance {
        val INSTANCE get() = instance
    }
}