@file:Suppress("UnstableApiUsage")

package dev.slne.surf.advancements.paper.menu

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.layout
import dev.slne.surf.api.paper.inventory.framework.dsl.onItemClick
import dev.slne.surf.api.paper.inventory.framework.dsl.withItem
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.header
import dev.slne.surf.api.paper.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.paper.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.api.paper.inventory.framework.view.state.get
import dev.slne.surf.api.paper.inventory.framework.view.state.initialState
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import io.papermc.paper.datacomponent.DataComponentTypes

val advancementView = paginatedSurfView("aaa") {
    val advancementExperienceState = initialState<AdvancementExperience>("advancement_progress")

    layoutTarget('L')

    pagination {
        lazySource { ctx ->
            val state = advancementExperienceState[ctx]

            state.advancement.getLevels()
        }

        elementFactory { context, builder, _, level ->
            val state = advancementExperienceState[context]
            val levelState = state.checkLevel(level.level)

            builder.withItem(levelState.itemType, level.level) {
                displayName {
                    primary("Level ${level.level}".toSmallCaps())
                }

                lore(level.buildLore(state))

                setData(DataComponentTypes.MAX_STACK_SIZE, state.advancement.getLevels().size.coerceAtLeast(1))
            }.onItemClick {
                // OPEN LEVEL INFO PAGE
            }
        }
    }

    settings {
        paginationViewRows(PaginationViewRows.TWO)
    }

    onInit {
        layout {
            empty()
            row("  LLLLL  ")
            row("  LLLLL  ")
            empty()
        }
    }

    onOpen {
        modifyContainer {
            header(advancementExperienceState[this].advancement.name)
        }
    }
}