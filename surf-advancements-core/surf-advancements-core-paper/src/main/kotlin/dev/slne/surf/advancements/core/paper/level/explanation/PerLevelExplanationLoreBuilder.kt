package dev.slne.surf.advancements.core.paper.level.explanation

import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience

object PerLevelExplanationLoreBuilder : ExplanationLoreBuilder {
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

        val percent = (xpIntoLevel.toDouble() / xpForThisLevel * 100.0).coerceIn(0.0, 100.0)

        appendExperienceLine(xpIntoLevel, xpForThisLevel)

        appendProgressBar(
            (percent / 100.0 * amountOfBars).toInt().coerceIn(0, amountOfBars),
            percent.toInt(),
            amountOfBars
        )
    }
}