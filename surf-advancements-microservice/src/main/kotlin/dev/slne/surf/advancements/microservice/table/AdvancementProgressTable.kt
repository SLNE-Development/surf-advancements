package dev.slne.surf.advancements.microservice.table

import dev.slne.surf.database.columns.nativeUuid
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.Table

object AdvancementProgressTable : Table("advancement_progresses") {
    val playerUuid = nativeUuid("player_uuid")
    val advancementId = varchar("advancement_id", 255)
    val progress = integer("advancement_progress")

    override val primaryKey = PrimaryKey(playerUuid, advancementId)
}