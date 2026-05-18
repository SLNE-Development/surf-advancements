package dev.slne.surf.advancements.core.common.packet.request

import dev.slne.surf.advancements.api.progress.AdvancementProgress
import dev.slne.surf.rabbitmq.api.packet.RabbitRequestPacket
import dev.slne.surf.rabbitmq.api.packet.standard.response.primitive.PrimitiveResponse
import kotlinx.serialization.Serializable

@Serializable
data class SaveAdvancementProgressPacket(
    val progress: AdvancementProgress
) : RabbitRequestPacket<PrimitiveResponse.BooleanResponsePacket>()
