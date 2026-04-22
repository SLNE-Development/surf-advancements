package dev.slne.surf.advancements.paper.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.layout
import dev.slne.surf.api.paper.inventory.framework.dsl.layoutSlot
import dev.slne.surf.api.paper.inventory.framework.dsl.onItemClick
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.blockRow
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconColor
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconType
import dev.slne.surf.api.paper.inventory.framework.view.icon.viewIcon
import dev.slne.surf.api.paper.inventory.framework.view.state.get
import dev.slne.surf.api.paper.inventory.framework.view.state.initialState
import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.api.paper.manager.AdvancementManager
import dev.slne.surf.advancements.api.paper.advancements.*
import dev.slne.surf.advancements.core.paper.experience.AdvancementExperienceImpl
import dev.slne.surf.advancements.paper.menu.utils.MenuHeads
import it.unimi.dsi.fastutil.objects.ObjectList
import me.devnatan.inventoryframework.context.RenderContext
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.reflect.KClass


val advancementsView = surfView("Advancements") {
    val playerUuidState = initialState<UUID>("player_uuid")
    val advancementExperienceState = initialState<ObjectList<AdvancementExperience>>("advancement_progress")

    settings {
        navigateBackOnOutsideClick(false)
    }

    containerDefaults {
        blockRow(1)
        blockRow(2, exemptColumns = intArrayOf(1, 3, 5, 7))
        blockRow(3)
        blockRow(4, exemptColumns = intArrayOf(1, 3, 5, 7))
        blockRow(5)
    }

    onInit {
        layout {
            empty()
            row(" C M W F ")
            empty()
            row(" I E A N ")
            row("    X    ")
        }
    }

    fun <S : Advancement> RenderContext.buildSkillItem(
        skill: S,
    ): Pair<AdvancementExperience, ItemStack> {
        val playerUuid = playerUuidState[this]

        val skillProgresses = advancementExperienceState[this]
        val skillProgressWithSkill =
            skillProgresses.firstOrNull { skill.javaClass.isInstance(it.advancement) }
                ?: run {
                    AdvancementExperienceImpl(
                        uuid = playerUuid,
                        advancement = skill,
                        currentExperience = 0
                    )
                }

        return skillProgressWithSkill to skillProgressWithSkill.advancement.displayItemStack(
            skillProgressWithSkill
        )
    }

    fun <S : Advancement> RenderContext.renderSlot(
        skillClass: KClass<S>,
        slot: Char,
    ) {
        val skill = AdvancementManager.getAdvancement(skillClass) ?: return
        val skillItem = buildSkillItem(skill)

        layoutSlot(slot, skillItem.second)
            .onItemClick {
                openForPlayer(advancementView, mapOf("advancement_progress" to skillItem.first))
            }
    }

    onFirstRender {
        layoutSlot('X') {
            withItem(MenuHeads.CROSS.apply {
                displayName {
                    primary("Schliessen".toSmallCaps())
                }
            })
        }

        renderSlot(PlaytimeAdvancement::class, 'M')

        layoutSlot('X', viewIcon(ViewIconType.CROSS, ViewIconColor.RED) {
            displayName {
                primary("Schliessen".toSmallCaps())
            }
        }).onItemClick {
            closeForPlayer()
        }
    }
}