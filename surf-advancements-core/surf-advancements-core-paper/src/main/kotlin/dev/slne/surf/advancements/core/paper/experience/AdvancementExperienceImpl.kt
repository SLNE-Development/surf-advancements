package dev.slne.surf.advancements.core.paper.experience

import dev.slne.surf.advancements.api.common.experience.SimpleExperience
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.AdvancementInstance
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.level.LevelState
import dev.slne.surf.advancements.api.paper.manager.AdvancementManager
import java.util.*

data class AdvancementExperienceImpl(
    override val uuid: UUID,
    override val advancement: Advancement,
    override var currentExperience: Int,
) : AdvancementExperience {
    override val currentLevel: Int
        get() = advancement.getLevels()
            .filter { currentExperience >= it.requiredExperience }
            .maxOfOrNull { it.level } ?: 0

    override fun checkLevel(level: Int): LevelState {
        val currentLevel = currentLevel

        return when {
            currentLevel < level -> LevelState.UNREACHED
            level == currentLevel -> LevelState.CURRENT
            else -> LevelState.REACHED
        }
    }

    override fun incrementExperience(amount: Int): AdvancementExperience {
        val beforeAdding = currentLevel
        currentExperience += amount
        val afterAdding = currentLevel

        if (beforeAdding != afterAdding) {
            AdvancementInstance.launch {
                advancement.awardLevelUpRewards(uuid, afterAdding)
            }
        }

        return this
    }
}

fun AdvancementExperience.simple() = SimpleExperience(
    uuid = uuid,
    advancementName = advancement.name,
    currentExperience = currentExperience
)

fun SimpleExperience.experience() = AdvancementExperienceImpl(
    uuid = uuid,
    advancement = AdvancementManager.getAdvancementByName(advancementName)
        ?: error("Trying to deserialize experience for unregistered advancement $advancementName"),
    currentExperience = currentExperience
)
