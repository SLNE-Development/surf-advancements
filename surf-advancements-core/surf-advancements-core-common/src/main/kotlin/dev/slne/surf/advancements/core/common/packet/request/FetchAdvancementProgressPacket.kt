package dev.slne.surf.advancements.core.common.packet.request

import dev.slne.surf.advancements.core.common.packet.response.ManyAdvancementProgressResponsePacket
import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import kotlinx.serialization.Serializable

@Serializable
data class FetchAdvancementProgressPacket(
    val playerUuid: SerializableUUID
) : RabbitRequestPacket<ManyAdvancementProgressResponsePacket>()
