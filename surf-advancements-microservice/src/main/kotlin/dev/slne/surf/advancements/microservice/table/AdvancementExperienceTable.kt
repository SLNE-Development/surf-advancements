package dev.slne.surf.advancements.microservice.table

import dev.slne.surf.database.columns.nativeUuid
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.Table

object AdvancementExperienceTable : Table("advancement_experiences") {
    val uuid = nativeUuid("uuid")

    val advancementName = varchar("advancement_name", 255)
    val experience = integer("experience")

    override val primaryKey = PrimaryKey(uuid, advancementName)
}