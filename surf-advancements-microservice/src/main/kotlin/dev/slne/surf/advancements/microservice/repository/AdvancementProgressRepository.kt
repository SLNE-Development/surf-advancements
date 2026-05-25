package dev.slne.surf.advancements.microservice.repository

import dev.slne.surf.advancements.api.progress.AdvancementProgress
import dev.slne.surf.advancements.microservice.table.AdvancementProgressTable
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.upsert
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import net.kyori.adventure.key.Key
import java.util.UUID

object AdvancementProgressRepository {

    suspend fun findProgresses(uuid: UUID): List<AdvancementProgress> = suspendTransaction {
        AdvancementProgressTable.selectAll()
            .where { AdvancementProgressTable.playerUuid eq uuid }
            .mapNotNull { row ->
                AdvancementProgress(
                    playerUuid = row[AdvancementProgressTable.playerUuid],
                    advancementId = Key.key(row[AdvancementProgressTable.advancementId]),
                    progress = row[AdvancementProgressTable.progress],
                    requiredProgress = 0
                )
            }
            .toList()
    }

    suspend fun saveProgress(progress: AdvancementProgress): Boolean = suspendTransaction {
        AdvancementProgressTable.upsert {
            it[playerUuid] = progress.playerUuid
            it[advancementId] = progress.advancementId.asString()
            it[AdvancementProgressTable.progress] = progress.progress
        }
        true
    }
}
