package dev.slne.surf.advancements.microservice.repository

import dev.slne.surf.api.core.util.toMutableObjectList
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.upsert
import dev.slne.surf.advancements.api.common.experience.SimpleExperience
import dev.slne.surf.advancements.microservice.table.AdvancementExperienceTable
import it.unimi.dsi.fastutil.objects.ObjectList
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import java.util.*

object ExperienceRepository {
    suspend fun findExperiences(uuid: UUID): ObjectList<SimpleExperience> = suspendTransaction {
        AdvancementExperienceTable.selectAll()
            .where { AdvancementExperienceTable.uuid eq uuid }
            .mapNotNull {
                val skillName = it[AdvancementExperienceTable.advancementName]
                val experience = it[AdvancementExperienceTable.experience]

                SimpleExperience(
                    uuid = uuid,
                    advancementName = skillName,
                    currentExperience = experience,
                )
            }.toList().toMutableObjectList()
    }

    suspend fun saveExperience(
        uuid: UUID,
        skillExperience: SimpleExperience
    ) = suspendTransaction {
        val skillName = skillExperience.advancementName
        val experience = skillExperience.currentExperience

        AdvancementExperienceTable.upsert {
            it[AdvancementExperienceTable.uuid] = uuid
            it[AdvancementExperienceTable.advancementName] = skillName
            it[AdvancementExperienceTable.experience] = experience
        }

        true
    }
}