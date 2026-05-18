package dev.slne.surf.advancements.core.paper

import dev.slne.surf.rabbitmq.api.ClientRabbitMQApi
import kotlinx.coroutines.CoroutineScope
import java.nio.file.Path

class PaperLoader(
    dataPath: Path,
    val scope: CoroutineScope
) {
    val rabbitApi = ClientRabbitMQApi.create("surf-advancements", dataPath)

    suspend fun onLoad() {
        rabbitApi.freezeAndConnect()
    }

    suspend fun onDisable() {
        rabbitApi.disconnect()
    }
}