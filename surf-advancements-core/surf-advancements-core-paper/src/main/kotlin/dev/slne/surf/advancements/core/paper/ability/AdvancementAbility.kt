package dev.slne.surf.advancements.core.paper.ability

import dev.slne.surf.advancements.api.paper.Advancement
import net.kyori.adventure.text.Component

/**
 * Describes a skill ability and its scaling behavior for display purposes.
 *
 * @param displayName the localized display name of the ability
 * @param description a short German description of what the ability does
 * @param minLevel the minimum skill level at which this ability activates
 * @param maxValue the maximum effect value at max level
 * @param maxLevel the maximum skill level (default 50)
 * @param valueFormatter formats the calculated ability value into a human-readable string
 */
data class AdvancementAbility(
    val displayName: Component,
    val description: String = "",
    val minLevel: Int,
    val maxValue: Double,
    val maxLevel: Int = Advancement.MAX_ADVANCEMENT_LEVEL,
    val valueFormatter: (Double) -> String
) {
    /**
     * Calculates the ability value at a given level using linear interpolation.
     * Mirrors [AbilityUtil.calculateScaledValue].
     */
    fun getValueAtLevel(level: Int): Double {
        if (level < minLevel) return 0.0
        val effectiveLevel = (level + 1).coerceAtMost(maxLevel + 1)
        return maxValue * (effectiveLevel - minLevel).toDouble() / (maxLevel + 1 - minLevel)
    }

    fun getFormattedValue(level: Int): String {
        return valueFormatter(getValueAtLevel(level))
    }

    fun isActiveAtLevel(level: Int): Boolean {
        return level >= minLevel
    }

    companion object {
        fun percentageFormatter(decimals: Int = 2): (Double) -> String = { value ->
            "%.${decimals}f%%".format(value * 100)
        }

        fun secondsFormatter(decimals: Int = 1): (Double) -> String = { value ->
            "%.${decimals}fs".format(value)
        }
    }
}
