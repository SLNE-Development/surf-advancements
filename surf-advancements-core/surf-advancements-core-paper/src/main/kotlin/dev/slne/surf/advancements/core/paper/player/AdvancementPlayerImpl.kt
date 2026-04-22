package dev.slne.surf.advancements.core.paper.player

import com.github.benmanes.caffeine.cache.Caffeine
import com.sksamuel.aedile.core.expireAfterWrite
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObjectListOf
import dev.slne.surf.api.paper.extensions.server
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.manager.AdvancementManager
import dev.slne.surf.advancements.api.paper.player.AdvancementPlayer
import dev.slne.surf.advancements.core.paper.experience.AdvancementExperienceImpl
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.sound.Sound
import java.util.*
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds
import org.bukkit.Sound as BukkitSound

class AdvancementPlayerImpl(
    override val uuid: UUID,
    experiences: ObjectList<AdvancementExperience>
) : AdvancementPlayer {
    private val _experiences = mutableObjectListOf<AdvancementExperience>(experiences)
    override val experiences = _experiences.freeze()

    override val player get() = server.getPlayer(uuid)
    override val offlinePlayer get() = server.getOfflinePlayer(uuid)

    private val pickUpCache = Caffeine
        .newBuilder()
        .expireAfterWrite(3.seconds)
        .build<Pair<UUID, Advancement>, Int>()

    override fun <S : Advancement> hasLevel(clazz: KClass<out S>, level: Int): Boolean =
        (findExperience(clazz)?.currentLevel ?: 0) >= level

    override fun <S : Advancement> findExperience(clazz: KClass<out S>): AdvancementExperience? =
        experiences.firstOrNull { clazz.isInstance(it.advancement) }

    override fun <S : Advancement> findOrCreateExperience(clazz: KClass<out S>): AdvancementExperience {
        val old = findExperience(clazz)
        if (old != null) return old

        val skill = AdvancementManager.getAdvancement(clazz)
            ?: error("Trying to access unregistered skill ${clazz.simpleName ?: "Unknown Skill Class"}")

        val new = AdvancementExperienceImpl(
            uuid = uuid,
            advancement = skill,
            currentExperience = 0
        )

        _experiences.add(new)

        return new
    }

    override fun <S : Advancement> incrementExperience(clazz: KClass<out S>, amount: Int) {
        if (amount < 1) {
            return
        }

        val experience = findOrCreateExperience(clazz).incrementExperience(amount)

        player?.playSound(true) {
            type(BukkitSound.ENTITY_EXPERIENCE_ORB_PICKUP)
            volume(.25f)
            pitch(1.25f)
            source(Sound.Source.AMBIENT)
        }

        val cachedValue = pickUpCache.getIfPresent(uuid to experience.advancement) ?: 0
        val newValue = cachedValue + amount
        pickUpCache.put(uuid to experience.advancement, newValue)

        player?.sendActionBar(buildText {
            spacer("»")
            appendSpace()
            append(experience.advancement.displayName)
            appendSpace()
            spacer("‖")
            appendSpace()
            success("+$newValue XP")
            appendSpace()
            spacer("«")
        })
    }
}