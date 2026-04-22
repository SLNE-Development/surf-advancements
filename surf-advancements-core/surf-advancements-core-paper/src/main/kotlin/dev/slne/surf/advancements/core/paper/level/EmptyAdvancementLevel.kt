package dev.slne.surf.advancements.core.paper.level

import dev.slne.surf.advancements.api.paper.Advancement

class EmptyAdvancementLevel(
    advancement: Advancement,
    level: Int,
    requiredExperience: Int,
) : AbstractAdvancementLevel(
    advancement = advancement,
    level = level,
    requiredExperience = requiredExperience,
)