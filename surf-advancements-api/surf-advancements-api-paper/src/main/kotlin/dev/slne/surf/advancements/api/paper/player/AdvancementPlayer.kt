package dev.slne.surf.advancements.api.paper.player

import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.jetbrains.annotations.Unmodifiable
import java.util.*
import kotlin.reflect.KClass

interface AdvancementPlayer {
    val uuid: UUID

    val player: Player?
    val offlinePlayer: OfflinePlayer

    val experiences: @Unmodifiable ObjectList<AdvancementExperience>

    fun <S : Advancement> hasLevel(clazz: KClass<out S>, level: Int): Boolean

    fun <S : Advancement> findExperience(clazz: KClass<out S>): AdvancementExperience?
    fun <S : Advancement> findOrCreateExperience(clazz: KClass<out S>): AdvancementExperience

    fun <S : Advancement> incrementExperience(clazz: KClass<out S>, amount: Int)
}

suspend fun Player.advancementPlayer() = AdvancementPlayerManager.fetchOrCreatePlayer(uniqueId)

inline fun <reified S : Advancement> AdvancementPlayer.hasLevel(level: Int): Boolean =
    hasLevel(S::class, level)

inline fun <reified S : Advancement> AdvancementPlayer.findExperience(): AdvancementExperience? =
    findExperience(S::class)

inline fun <reified S : Advancement> AdvancementPlayer.findOrCreateExperience(): AdvancementExperience =
    findOrCreateExperience(S::class)

inline fun <reified S : Advancement> AdvancementPlayer.incrementExperience(amount: Int) =
    incrementExperience(S::class, amount)