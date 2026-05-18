package dev.slne.surf.advancements.core.common

import dev.slne.surf.api.core.util.requiredService
import dev.slne.surf.rabbitmq.api.RabbitMQApi
import kotlinx.coroutines.CoroutineScope

private val instance = requiredService<AdvancementInstance>()

interface AdvancementInstance {
    val pluginScope: CoroutineScope
    val rabbitApi: RabbitMQApi

    companion object : AdvancementInstance by instance {
        val INSTANCE get() = instance
    }
}