@file:Suppress("UnstableApiUsage")

package dev.slne.surf.advancements.paper.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.layout
import dev.slne.surf.api.paper.inventory.framework.dsl.layoutSlot
import dev.slne.surf.api.paper.inventory.framework.dsl.onItemClick
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.dsl.withItem
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.blockRow
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.header
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconColor
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconType
import dev.slne.surf.api.paper.inventory.framework.view.icon.viewIcon
import dev.slne.surf.api.paper.inventory.framework.view.pagination.AbstractPaginatedSurfView
import dev.slne.surf.api.paper.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.paper.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.api.paper.inventory.framework.view.state.get
import dev.slne.surf.api.paper.inventory.framework.view.state.initialState
import dev.slne.surf.advancements.core.paper.PaperAdvancement
import dev.slne.surf.advancements.core.paper.PaperAdvancementCategory
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import dev.slne.surf.advancements.paper.menu.utils.MenuHeads
import org.bukkit.inventory.ItemType
import java.util.UUID

val advancementCategoryView: AbstractPaginatedSurfView = paginatedSurfView("advancements_category") {
    val playerUuidState = initialState<UUID>("player_uuid")
    val categoryState = initialState<PaperAdvancementCategory>("category")

    layoutTarget('A')

    pagination {
        lazySource { ctx ->
            categoryState[ctx].members.toList()
        }

        elementFactory { context, builder, _, advancement ->
            val playerUuid = playerUuidState[context]
            val progress = AdvancementProgressManager.getProgress(playerUuid, advancement)
            val currentProgress = progress?.progress ?: 0
            val requiredProgress = advancement.requiredProgress
            val isComplete = currentProgress >= requiredProgress && requiredProgress > 0

            val itemType = if (advancement is PaperAdvancement) advancement.displayItem else ItemType.STONE

            builder.withItem(itemType) {
                displayName {
                    if (isComplete) success(advancement.name.toSmallCaps())
                    else primary(advancement.name.toSmallCaps())
                }
                lore(buildText { gray(advancement.description) })
                if (requiredProgress > 1) {
                    lore(buildText {
                        gray("Fortschritt: ")
                        variableValue("$currentProgress/$requiredProgress")
                    })
                }
                lore(buildText {
                    if (isComplete) success("✓ Abgeschlossen")
                    else error("✗ Noch nicht abgeschlossen")
                })
            }
        }
    }

    settings {
        paginationViewRows(PaginationViewRows.TWO)
        navigateBackOnOutsideClick(false)
    }

    containerDefaults {
        blockRow(4)
    }

    onInit {
        layout {
            empty()
            row("  AAAAA  ")
            row("  AAAAA  ")
            row("B       X")
        }
    }

    onFirstRender {
        layoutSlot('X', viewIcon(ViewIconType.CROSS, ViewIconColor.RED) {
            displayName { primary("Schließen".toSmallCaps()) }
        }).onItemClick { closeForPlayer() }

        layoutSlot('B', MenuHeads.ARROW_LEFT.apply {
            displayName { primary("Zurück".toSmallCaps()) }
        }).onItemClick {
            openForPlayer(
                advancementsView,
                mapOf("player_uuid" to playerUuidState[this])
            )
        }
    }

    onOpen {
        modifyContainer { header(categoryState[this].name.toSmallCaps()) }
    }
}
