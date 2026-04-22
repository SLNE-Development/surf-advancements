package dev.slne.surf.advancements.core.paper.player

import com.github.benmanes.caffeine.cache.Caffeine
import com.google.auto.service.AutoService
import com.sksamuel.aedile.core.asLoadingCache
import dev.slne.surf.advancements.api.paper.player.AdvancementPlayer
import dev.slne.surf.advancements.api.paper.player.AdvancementPlayerManager
import dev.slne.surf.advancements.core.paper.experience.ExperienceService
import net.kyori.adventure.util.Services
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@AutoService(AdvancementPlayerManager::class)
class AdvancementPlayerManagerImpl : AdvancementPlayerManager, Services.Fallback {
    private val syncCache = ConcurrentHashMap<UUID, AdvancementPlayer>()

    private val cache = Caffeine.newBuilder()
        .maximumSize(10_000)
        .asLoadingCache<UUID, AdvancementPlayer> { uuid ->
            val experiences = ExperienceService.fetchPlayerExperience(uuid)

            AdvancementPlayerImpl(uuid, experiences).also { syncCache[uuid] = it }
        }

    override suspend fun fetchOrCreatePlayer(uuid: UUID): AdvancementPlayer =
        cache.get(uuid)

    override fun getPlayerIfCached(uuid: UUID): AdvancementPlayer? =
        syncCache[uuid]

    override suspend fun savePlayer(uuid: UUID) {
        val player = cache.getIfPresent(uuid) ?: return

        savePlayer(player)
    }

    override suspend fun savePlayer(player: AdvancementPlayer) {
        ExperienceService.savePlayerExperience(player.uuid, player.experiences)
    }

    override fun invalidatePlayer(uuid: UUID) {
        cache.invalidate(uuid)
        syncCache.remove(uuid)
    }
}