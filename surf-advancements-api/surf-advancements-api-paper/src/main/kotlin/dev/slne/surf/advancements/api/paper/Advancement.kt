package dev.slne.surf.advancements.api.paper

import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.advancements.api.common.InternalAdvancementApi
import dev.slne.surf.advancements.api.common.curve.ExperienceCurve
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.level.AdvancementLevel
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType
import org.jetbrains.annotations.Unmodifiable
import java.util.*

interface Advancement : ComponentLike {
    val name: String
    val displayName: Component
    val lore: LoreBuilder.() -> Unit
    val material: ItemType

    val experienceCurve: ExperienceCurve
    val baseExperience: Int
    val maxExperience: Int
    val maxLevel: Int
    val active: Boolean

    fun getLevels(): ObjectList<AdvancementLevel>
    fun displayItemStack(progress: AdvancementExperience): ItemStack

    suspend fun awardLevelUpRewards(uuid: UUID, level: Int)

    @InternalAdvancementApi
    val listeners: @Unmodifiable ObjectList<Listener>

    @InternalAdvancementApi
    fun registerListeners()

    companion object {
        const val BASE_EXPERIENCE = 100
        const val MAX_ADVANCEMENT_LEVEL = 50
        const val MAX_EXPERIENCE = 5_000_000
    }
}