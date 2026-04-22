package dev.slne.surf.advancements.core.common.rabbit.packet.request

import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.rabbitmq.api.packet.standard.response.primitive.PrimitiveResponse
import dev.slne.surf.advancements.api.common.experience.SimpleExperience
import kotlinx.serialization.Serializable

@Serializable
data class SaveExperienceRequestPacket(
    val playerUuid: SerializableUUID,
    val simpleExperiences: List<SimpleExperience>
) : RabbitRequestPacket<PrimitiveResponse.BooleanResponsePacket>()
