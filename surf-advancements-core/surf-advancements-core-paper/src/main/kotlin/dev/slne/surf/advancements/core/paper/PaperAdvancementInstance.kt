package dev.slne.surf.advancements.core.paper

import dev.slne.surf.advancements.core.common.AdvancementInstance
import dev.slne.surf.rabbitmq.api.ClientRabbitMQApi
import kotlinx.coroutines.CoroutineScope

interface PaperAdvancementInstance : AdvancementInstance {
    val paperLoader: PaperLoader

    override val rabbitApi: ClientRabbitMQApi get() = paperLoader.rabbitApi
    override val pluginScope: CoroutineScope get() = paperLoader.scope


    companion object :
        PaperAdvancementInstance by AdvancementInstance.INSTANCE as PaperAdvancementInstance {
        val INSTANCE get() = AdvancementInstance.INSTANCE
    }
}