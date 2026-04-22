package dev.slne.surf.advancements.api.paper.experience

import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.level.LevelState
import java.util.*

interface AdvancementExperience {
    val uuid: UUID
    val advancement: Advancement

    val currentExperience: Int
    val currentLevel: Int

    fun checkLevel(level: Int): LevelState
    fun incrementExperience(amount: Int): AdvancementExperience
}