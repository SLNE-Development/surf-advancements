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
import dev.slne.surf.advancements.core.paper.PaperAdvancementCategory
import dev.slne.surf.advancements.core.paper.manager.AdvancementManager
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import java.util.UUID

val advancementsView: AbstractPaginatedSurfView = paginatedSurfView("advancements_overview") {
    val playerUuidState = initialState<UUID>("player_uuid")

    layoutTarget('C')

    pagination {
        lazySource { _ ->
            AdvancementManager.categories.filterIsInstance<PaperAdvancementCategory>()
        }

        elementFactory { context, builder, _, category ->
            val playerUuid = playerUuidState[context]
            val completedCount = category.members.count { advancement ->
                AdvancementProgressManager.isCompleted(playerUuid, advancement)
            }
            val totalCount = category.members.size

            builder.withItem(category.displayItem) {
                displayName { primary(category.name.toSmallCaps()) }
                lore(buildText {
                    gray("$completedCount/$totalCount Erfolge abgeschlossen")
                })
            }.onItemClick {
                openForPlayer(
                    advancementCategoryView,
                    mapOf(
                        "category" to category,
                        "player_uuid" to playerUuid
                    )
                )
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
            row("  CCCCC  ")
            row("  CCCCC  ")
            row("        X")
        }
    }

    onFirstRender {
        layoutSlot('X', viewIcon(ViewIconType.CROSS, ViewIconColor.RED) {
            displayName { primary("Schliessen".toSmallCaps()) }
        }).onItemClick { closeForPlayer() }
    }

    onOpen {
        modifyContainer { header("Erfolge".toSmallCaps()) }
    }
}
