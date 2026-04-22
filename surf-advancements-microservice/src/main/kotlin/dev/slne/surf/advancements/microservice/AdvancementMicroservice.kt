package dev.slne.surf.advancements.microservice

import com.google.auto.service.AutoService
import dev.slne.surf.database.DatabaseApi
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.microservice.api.microservice.Microservice
import dev.slne.surf.rabbitmq.api.ServerRabbitMQApi
import dev.slne.surf.advancements.microservice.handler.AdvancementExperienceHandler
import dev.slne.surf.advancements.microservice.table.AdvancementExperienceTable
import kotlin.io.path.Path

@AutoService(Microservice::class)
class AdvancementMicroservice : Microservice() {
    override val dataPath = Path("config")
    private val databaseApi = DatabaseApi.create(dataPath)
    private val rabbitApi = ServerRabbitMQApi.create("surf-advancements", dataPath)

    override suspend fun onBootstrap(args: List<String>) {
        suspendTransaction {
            SchemaUtils.create(
                AdvancementExperienceTable
            )
        }

        rabbitApi.registerRequestHandler(AdvancementExperienceHandler)
        rabbitApi.freezeAndConnect()
    }

    override suspend fun onDisable() {
        rabbitApi.disconnect()
        databaseApi.shutdown()
    }
}