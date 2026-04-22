package dev.slne.surf.advancements.core.paper.level

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObjectListOf
import dev.slne.surf.api.core.util.objectListOf
import dev.slne.surf.api.core.util.toObjectList
import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.level.AdvancementLevel
import dev.slne.surf.advancements.api.paper.level.reward.LevelReward
import dev.slne.surf.advancements.core.paper.level.explanation.ExplanationLoreBuilder
import dev.slne.surf.advancements.core.paper.level.explanation.PerLevelExplanationLoreBuilder
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

fun advancementLevel(
    advancement: Advancement,
    level: Int,
    requiredExperience: Int,
    rewards: ObjectList<LevelReward>.() -> Unit = {},
    description: (LoreBuilder.() -> Unit)? = null,
) = object : AbstractAdvancementLevel(
    advancement = advancement,
    level = level,
    requiredExperience = requiredExperience,
    description = description,
) {
    override fun buildRewards(): ObjectList<LevelReward> {
        return mutableObjectListOf<LevelReward>().apply(rewards)
    }
}

open class AbstractAdvancementLevel(
    override val advancement: Advancement,
    override val level: Int,
    override val requiredExperience: Int,
    override val description: (LoreBuilder.() -> Unit)? = null,
) : AdvancementLevel {
    private val _rewards = buildRewards()
    override val rewards get() = _rewards.freeze()

    override fun buildLore(experience: AdvancementExperience): ObjectList<Component> {
        return LoreBuilder().apply {
            emptyLine()
            buildDescriptionLore()
            buildLevelExplanationLore(experience)
            buildRewardLore()
            emptyLine()
        }.build().toObjectList()
    }

    private fun LoreBuilder.buildLevelExplanationLore(experience: AdvancementExperience) {
        PerLevelExplanationLoreBuilder.run {
            buildExplanation(
                experience = experience,
                level = level,
                amountOfBars = ExplanationLoreBuilder.AMOUNT_OF_BARS
            )
        }
    }

    private fun LoreBuilder.buildDescriptionLore() {
        if (description != null) {
            description?.invoke(this)
            emptyLine()
        }
    }

    private fun LoreBuilder.buildRewardLore() {
        line {
            primary("Belohnungen:".toSmallCaps())
        }
        emptyLine()

        rewards.forEach { reward ->
            line {
                spacer("- ")
                append(reward.displayName)
            }

            reward.description(this)
        }

        if (rewards.isEmpty()) {
            line {
                spacer("Keine Belohnungen".toSmallCaps())
            }
        }
    }


    open fun buildRewards(): ObjectList<LevelReward> {
        return objectListOf()
    }

    override suspend fun grantRewards(player: Player) {
        rewards.forEach { it.grant(player) }
    }
}