package dev.slne.surf.advancements.microservice.handler

import dev.slne.surf.advancements.core.common.packet.request.FetchAdvancementProgressPacket
import dev.slne.surf.advancements.core.common.packet.request.SaveAdvancementProgressPacket
import dev.slne.surf.advancements.core.common.packet.response.ManyAdvancementProgressResponsePacket
import dev.slne.surf.advancements.microservice.repository.AdvancementProgressRepository
import dev.slne.surf.rabbitmq.api.handler.RabbitHandler
import dev.slne.surf.rabbitmq.api.packet.standard.response.primitive.PrimitiveResponse
import kotlinx.coroutines.launch

object AdvancementProgressHandler {

    @RabbitHandler
    fun handleFetchProgressPacket(packet: FetchAdvancementProgressPacket) = packet.launch {
        packet.respond(
            ManyAdvancementProgressResponsePacket(
                AdvancementProgressRepository.findProgresses(packet.playerUuid)
            )
        )
    }

    @RabbitHandler
    fun handleSaveProgressPacket(packet: SaveAdvancementProgressPacket) = packet.launch {
        packet.respond(
            PrimitiveResponse.BooleanResponsePacket(
                AdvancementProgressRepository.saveProgress(packet.progress)
            )
        )
    }
}
