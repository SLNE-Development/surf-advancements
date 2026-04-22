package dev.slne.surf.advancements.paper

import com.github.shynixn.mccoroutine.folia.*
import com.google.auto.service.AutoService
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.AdvancementInstance
import dev.slne.surf.advancements.core.paper.PaperLoader
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import dev.slne.surf.advancements.core.paper.experience.AdvancementExperienceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import net.kyori.adventure.util.Services
import org.bukkit.Location
import org.bukkit.entity.Entity
import java.util.*
import kotlin.coroutines.CoroutineContext

@AutoService(AdvancementInstance::class)
class AdvancementInstanceImpl : PaperAdvancementInstance, Services.Fallback {
    override fun launch(
        context: CoroutineContext,
        start: CoroutineStart,
        block: suspend CoroutineScope.() -> Unit
    ) = plugin.launch(context, start, block)

    override val globalRegionDispatcher by lazy {
        plugin.globalRegionDispatcher
    }
    override val asyncDispatcher by lazy {
        plugin.asyncDispatcher
    }
    override val mainDispatcher by lazy {
        plugin.mainDispatcher
    }

    override fun entityDispatcher(entity: Entity) = plugin.entityDispatcher(entity)
    override fun regionDispatcher(location: Location) = plugin.regionDispatcher(location)

    override fun createAdvancementExperience(
        uuid: UUID,
        advancement: Advancement,
        currentExperience: Int
    ) = AdvancementExperienceImpl(
        uuid = uuid,
        advancement = advancement,
        currentExperience = currentExperience
    )

    override val paperLoader = PaperLoader(plugin.dataPath)
}