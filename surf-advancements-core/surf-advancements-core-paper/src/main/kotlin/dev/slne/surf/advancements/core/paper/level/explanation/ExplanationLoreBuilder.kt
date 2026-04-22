package dev.slne.surf.advancements.core.paper.level.explanation

import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience

interface ExplanationLoreBuilder {
    fun LoreBuilder.buildExplanation(
        experience: AdvancementExperience,
        level: Int,
        amountOfBars: Int
    )

    fun LoreBuilder.appendExperienceLine(
        current: Int,
        max: Int
    ) {
        line {
            spacer("$current / $max XP")
        }
    }

    fun LoreBuilder.appendProgressBar(
        barsFilled: Int,
        percent: Int,
        amountOfBars: Int
    ) {
        line {
            for (i in 1..amountOfBars) {
                when {
                    i <= barsFilled -> success("|")
                    else -> error("|")
                }
            }
            appendSpace()
            spacer("($percent%)")
        }
    }

    companion object {
        const val AMOUNT_OF_BARS = 50
    }
}