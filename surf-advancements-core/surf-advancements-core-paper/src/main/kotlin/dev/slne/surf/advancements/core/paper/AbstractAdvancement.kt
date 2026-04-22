package dev.slne.surf.advancements.core.paper

import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.util.freeze
import dev.slne.surf.api.core.util.mutableObjectListOf
import dev.slne.surf.api.core.util.objectListOf
import dev.slne.surf.api.paper.builder.LoreBuilder
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.api.paper.extensions.server
import dev.slne.surf.api.paper.util.BukkitSound
import dev.slne.surf.advancements.api.common.InternalAdvancementApi
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.AdvancementInstance
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.level.AdvancementLevel
import dev.slne.surf.api.paper.nms.NmsUseWithCaution
import dev.slne.surf.api.paper.nms.SurfPaperNmsBridge
import dev.slne.surf.api.paper.nms.bridges.packets.player.SurfPaperNmsPlayerToastPackets
import dev.slne.surf.api.paper.nms.bridges.packets.player.toast.Toast
import dev.slne.surf.api.paper.nms.bridges.packets.player.toast.toast
import io.papermc.paper.advancement.AdvancementDisplay
import it.unimi.dsi.fastutil.objects.ObjectList
import kotlinx.coroutines.withContext
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemType
import java.util.*

@OptIn(InternalAdvancementApi::class)
abstract class AbstractAdvancement(
    override val name: String,
    override val material: ItemType,
    override val displayName: Component,
    override val lore: LoreBuilder.() -> Unit,
    override val active: Boolean = true,
    listeners: ObjectList<Listener> = objectListOf(),
) : Advancement {
    private val _listeners = mutableObjectListOf<Listener>(listeners)
    override val listeners get() = _listeners.freeze()

    val maxLevel: Int get() = getLevels().size

    open fun getExtraLevels(): ObjectList<AdvancementLevel> {
        return objectListOf()
    }

    override suspend fun awardLevelUpRewards(uuid: UUID, level: Int) {
        val player = server.getPlayer(uuid) ?: return
        val levelRewards = getLevels().firstOrNull { it.level == level }?.rewards ?: objectListOf()

        player.sendText {
            appendInfoPrefix()
            spacer("-".repeat(15))
            variableValue("LEVELUP", TextDecoration.BOLD)
            spacer("-".repeat(15))

            appendNewInfoPrefixedLine()

            appendNewInfoPrefixedLine()
            info("Du hast Level ")
            variableValue(level)
            info(" in ")
            append(displayName)
            info(" erreicht!")

            if (levelRewards.isNotEmpty()) {
                appendNewInfoPrefixedLine()
                appendNewInfoPrefixedLine()
                info("Belohnungen:")

                levelRewards.forEach { reward ->
                    appendNewInfoPrefixedLine()
                    info("  - ")
                    append(reward.displayName)
                }
            }

            appendNewInfoPrefixedLine()

            appendNewInfoPrefixedLine()
            spacer("-".repeat(15))
            variableValue("LEVELUP", TextDecoration.BOLD)
            spacer("-".repeat(15))
        }

        player.playSound(true) {
            type(BukkitSound.ENTITY_PLAYER_LEVELUP)
            source(Sound.Source.AMBIENT)
            volume(.5f)
            pitch(.25f)
        }

        player.playSound(true) {
            type(BukkitSound.ENTITY_FIREWORK_ROCKET_BLAST)
            source(Sound.Source.AMBIENT)
            volume(.5f)
        }

        player.playSound(true) {
            type(BukkitSound.ENTITY_FIREWORK_ROCKET_TWINKLE)
            source(Sound.Source.AMBIENT)
            volume(.5f)
        }

        val toast = toast {
            icon(material)
            title {
                append(displayName)
                appendNewline()
                info("Level ")
                variableValue(level)
            }

            frame(AdvancementDisplay.Frame.TASK)
        }

        @OptIn(NmsUseWithCaution::class)
        toast.createOperation().execute(player)

        if (levelRewards.isEmpty()) {
            return
        }

        withContext(AdvancementInstance.entityDispatcher(player)) {
            levelRewards.forEach { it.grant(player) }
        }
    }

    override fun getLevels(): ObjectList<AdvancementLevel> = getExtraLevels()

    override fun displayItemStack(progress: AdvancementExperience) = buildItem(material) {
        displayName(displayName)

        val currentLevel = progress.currentLevel

        buildLore {
            this@AbstractAdvancement.lore(this)
            emptyLine()

            line {
                variableKey("Level: ")
                variableValue(currentLevel)
            }

            if (!active) {
                emptyLine()
                line {
                    error("Diese Fähigkeit ist derzeit nicht verfügbar")
                }
            }
        }
    }

    override fun registerListeners() {
        _listeners.forEach { it.register() }
    }

    override fun asComponent() = displayName
}