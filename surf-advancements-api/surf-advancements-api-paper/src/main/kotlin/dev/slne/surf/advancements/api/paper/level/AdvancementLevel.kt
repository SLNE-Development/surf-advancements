package dev.slne.surf.advancements.api.paper.level

import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.level.reward.LevelReward
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.jetbrains.annotations.Unmodifiable

interface AdvancementLevel {
    val advancement: Advancement
    val level: Int
    val requiredExperience: Int

    val description: (LoreBuilder.() -> Unit)?
    fun buildLore(experience: AdvancementExperience): ObjectList<Component>

    val rewards: @Unmodifiable ObjectList<LevelReward>

    suspend fun grantRewards(player: Player)
}