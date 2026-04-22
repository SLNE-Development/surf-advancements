package dev.slne.surf.advancements.core.common.rabbit.packet.request

import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.advancements.core.common.rabbit.packet.response.ManySimpleExperienceResponsePacket
import kotlinx.serialization.Serializable

@Serializable
data class FindExperiencesRequestPacket(
    val playerUuid: SerializableUUID
) : RabbitRequestPacket<ManySimpleExperienceResponsePacket>()
