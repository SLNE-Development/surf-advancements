package dev.slne.surf.advancements.core.paper.level.explanation

import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience

object GlobalXPWithPerLevelBarLoreBuilder : ExplanationLoreBuilder {
    override fun LoreBuilder.buildExplanation(
        experience: AdvancementExperience,
        level: Int,
        amountOfBars: Int
    ) {
        val levels = experience.advancement.getLevels()
        val currentLevel = experience.currentLevel

        val thisLevelReqXp = levels.find { it.level == level }?.requiredExperience ?: 0
        val prevLevelReqXp = levels.find { it.level == level - 1 }?.requiredExperience ?: 0
        val xpForThisLevel = (thisLevelReqXp - prevLevelReqXp).coerceAtLeast(1)

        val xpIntoLevel = when {
            currentLevel > level -> xpForThisLevel
            currentLevel == level -> (experience.currentExperience - prevLevelReqXp).coerceAtLeast(0)
            else -> 0
        }

        appendExperienceLine(xpIntoLevel, xpForThisLevel)

        appendProgressBar(
            (xpIntoLevel.toDouble() / xpForThisLevel * amountOfBars).toInt().coerceIn(0, amountOfBars),
            (xpIntoLevel.toDouble() / xpForThisLevel * 100).toInt(),
            amountOfBars
        )
    }
}