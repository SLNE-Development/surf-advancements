@file:Suppress("UnstableApiUsage")

package dev.slne.surf.advancements.paper.menu

import dev.slne.surf.advancements.core.paper.PaperAdvancement
import dev.slne.surf.advancements.core.paper.PaperAdvancementCategory
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.layout
import dev.slne.surf.api.paper.inventory.framework.dsl.withItem
import dev.slne.surf.api.paper.inventory.framework.view.*
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.blockRow
import dev.slne.surf.api.paper.inventory.framework.view.container.dsl.header
import dev.slne.surf.api.paper.inventory.framework.view.pagination.AbstractPaginatedSurfView
import dev.slne.surf.api.paper.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.paper.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.api.paper.inventory.framework.view.state.get
import dev.slne.surf.api.paper.inventory.framework.view.state.initialState
import org.bukkit.inventory.ItemType
import java.util.*

val advancementCategoryView: AbstractPaginatedSurfView =
    paginatedSurfView("Erfolge") {
        val playerUuidState = initialState<UUID>("player_uuid")
        val categoryState = initialState<PaperAdvancementCategory>("category")

        layoutTarget('A')

        pagination {
            lazySource { ctx ->
                categoryState[ctx].members
                    .filter {
                        val playerUuid = playerUuidState[ctx]
                        val isDone = AdvancementProgressManager.isCompleted(playerUuid, it)

                        !it.hidden || isDone
                    }
                    .toList()
                    .sortedByDescending {
                        AdvancementProgressManager.isCompleted(
                            playerUuidState[ctx],
                            it
                        )
                    }
            }

            elementFactory { context, builder, _, advancement ->
                val playerUuid = playerUuidState[context]
                val progress = AdvancementProgressManager.getProgress(playerUuid, advancement)
                val currentProgress = progress?.progress ?: 0
                val requiredProgress = advancement.requiredProgress
                val isComplete = requiredProgress in 1..currentProgress

                val itemType =
                    if (advancement is PaperAdvancement) advancement.displayItem else ItemType.STONE

                builder.withItem(itemType) {
                    val percentage =
                        if (requiredProgress <= 0) 100
                        else ((currentProgress.toDouble() / requiredProgress.toDouble()) * 100)
                            .toInt()
                            .coerceIn(0, 100)

                    val bars = 20
                    val filledBars = (bars * percentage) / 100

                    val progressBar = buildString {
                        repeat(filledBars) { append("■") }
                        repeat(bars - filledBars) { append("□") }
                    }

                    displayName {
                        if (isComplete) {
                            success("✔ ")
                            primary(advancement.name.toSmallCaps())
                        } else {
                            error("✘ ")
                            primary(advancement.name.toSmallCaps())
                        }
                    }

                    buildLore {
                        line {
                            append(advancement.description)
                        }

                        emptyLine()

                        line {
                            note("Status: ")

                            if (isComplete) {
                                success("Abgeschlossen")
                            } else {
                                error("Nicht abgeschlossen")
                            }
                        }

                        if (requiredProgress > 1) {
                            emptyLine()

                            line {
                                note("Fortschritt")
                            }

                            line {
                                variableValue(progressBar)
                            }

                            line {
                                variableValue("$currentProgress/$requiredProgress")
                                spacer(" ")
                                gray("($percentage%)")
                            }
                        }

                        emptyLine()

                        line {
                            note("Kategorie: ")
                            variableValue(categoryState[context].name)
                        }

                        emptyLine()

                        if (isComplete) {
                            line {
                                success("Belohnung erhalten")
                            }
                        } else {
                            line {
                                gray("Noch nicht abgeschlossen")
                            }
                        }
                    }
                }
            }
        }

        settings {
            paginationViewRows(PaginationViewRows.TWO)
        }

        containerDefaults {
            blockRow(4)
        }

        onInit {
            layout {
                empty()
                row("  AAAAA  ")
                row("  AAAAA  ")
                empty()
            }
        }

        onOpen {
            modifyContainer { header(categoryState[this].name) }
        }
    }
