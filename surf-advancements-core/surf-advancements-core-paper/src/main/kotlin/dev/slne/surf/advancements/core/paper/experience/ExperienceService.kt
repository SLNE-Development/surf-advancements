package dev.slne.surf.advancements.core.paper.experience

import dev.slne.surf.api.core.util.toObjectList
import dev.slne.surf.advancements.api.paper.experience.AdvancementExperience
import dev.slne.surf.advancements.core.common.rabbit.packet.request.FindExperiencesRequestPacket
import dev.slne.surf.advancements.core.common.rabbit.packet.request.SaveExperienceRequestPacket
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import it.unimi.dsi.fastutil.objects.ObjectList
import java.util.*

object ExperienceService {
    suspend fun fetchPlayerExperience(uuid: UUID): ObjectList<AdvancementExperience> =
        PaperAdvancementInstance.rabbitApi.sendRequest(
            FindExperiencesRequestPacket(uuid)
        ).simpleExperiences.map {
            it.experience()
        }.toObjectList()

    suspend fun savePlayerExperience(
        uuid: UUID,
        experience: ObjectList<AdvancementExperience>
    ) = PaperAdvancementInstance.rabbitApi.sendRequest(
        SaveExperienceRequestPacket(
            playerUuid = uuid,
            simpleExperiences = experience.map { it.simple() }.toObjectList()
        )
    )
}