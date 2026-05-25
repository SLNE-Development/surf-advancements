@file:Suppress("UnstableApiUsage")

package dev.slne.surf.advancements.paper.menu

import dev.slne.surf.advancements.core.paper.PaperAdvancementCategory
import dev.slne.surf.advancements.core.paper.manager.AdvancementManager
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.onItemClick
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.blockRow
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.header
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconColor
import dev.slne.surf.api.paper.inventory.framework.view.icon.ViewIconType
import dev.slne.surf.api.paper.inventory.framework.view.icon.viewIcon
import dev.slne.surf.api.paper.inventory.framework.view.state.get
import dev.slne.surf.api.paper.inventory.framework.view.state.initialState
import java.util.*

val advancementsView: AbstractSurfView = surfView("Erfolge") {
    val playerUuidState = initialState<UUID>("player_uuid")

    onFirstRender {
        val playerUuid = playerUuidState[this]
        val survivalCategory =
            AdvancementManager.categories.filterIsInstance<PaperAdvancementCategory>().first()
        val completedCount = survivalCategory.members.count { advancement ->
            AdvancementProgressManager.isCompleted(playerUuid, advancement)
        }
        val totalCount = survivalCategory.members.size

        layoutSlot('S', buildItem(survivalCategory.displayItem) {
            displayName {
                primary("Survival Server".toSmallCaps())
            }

            val percentage =
                if (totalCount == 0) 0
                else ((completedCount.toDouble() / totalCount.toDouble()) * 100).toInt()

            val bars = 20
            val filledBars = (bars * percentage) / 100

            val progressBar = buildString {
                repeat(filledBars) { append("■") }
                repeat(bars - filledBars) { append("□") }
            }

            buildLore {
                line {
                    append(survivalCategory.description)
                }

                emptyLine()

                line {
                    note("Fortschritt")
                }

                line {
                    variableValue(progressBar)
                }

                line {
                    variableValue("$completedCount")
                    spacer(" / ")
                    variableValue("$totalCount")
                    spacer(" ")
                    gray("($percentage%)")
                }

                emptyLine()

                line {
                    when {
                        percentage >= 100 -> success("Meister aller Erfolge")
                        percentage >= 75 -> primary("Fast abgeschlossen")
                        percentage >= 50 -> note("Guter Fortschritt")
                        else -> gray("Beginn deiner Reise")
                    }
                }

                emptyLine()

                line {
                    primary("Klicke zum Öffnen")
                }
            }
        }).onClick { _ ->
            openForPlayer(
                advancementCategoryView,
                mapOf(
                    "category" to survivalCategory,
                    "player_uuid" to playerUuid
                )
            )
        }

        layoutSlot('X', viewIcon(ViewIconType.CROSS, ViewIconColor.RED) {
            displayName { primary("Schließen".toSmallCaps()) }
        }).onItemClick { closeForPlayer() }
    }

    settings {
        rows(4)
    }

    containerDefaults {
        blockRow(1)
        blockRow(2, exemptColumns = intArrayOf(4))
        blockRow(3)
        blockRow(4, exemptColumns = intArrayOf(4))
    }

    onInit {
        layout(
            "         ",
            "    S    ",
            "         ",
            "    X    "
        )
    }

    onOpen {
        modifyContainer { header("Erfolge") }
    }
}
