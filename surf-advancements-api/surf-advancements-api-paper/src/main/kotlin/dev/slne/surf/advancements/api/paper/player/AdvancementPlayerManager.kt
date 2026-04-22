package dev.slne.surf.advancements.api.paper.player

import dev.slne.surf.api.core.util.requiredService
import java.util.*

private val playerManager = requiredService<AdvancementPlayerManager>()

interface AdvancementPlayerManager {
    suspend fun fetchOrCreatePlayer(uuid: UUID): AdvancementPlayer

    fun getPlayerIfCached(uuid: UUID): AdvancementPlayer?

    suspend fun savePlayer(uuid: UUID)
    suspend fun savePlayer(player: AdvancementPlayer)

    fun invalidatePlayer(uuid: UUID)

    companion object : AdvancementPlayerManager by playerManager {
        val INSTANCE get() = playerManager
    }
}