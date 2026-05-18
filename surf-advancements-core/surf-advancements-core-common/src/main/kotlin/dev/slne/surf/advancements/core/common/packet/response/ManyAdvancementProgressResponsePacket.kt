package dev.slne.surf.advancements.core.common.packet.response

import dev.slne.surf.advancements.api.progress.AdvancementProgress
import dev.slne.surf.rabbitmq.api.packet.RabbitResponsePacket
import kotlinx.serialization.Serializable

@Serializable
data class ManyAdvancementProgressResponsePacket(
    val progresses: List<AdvancementProgress>
) : RabbitResponsePacket()
