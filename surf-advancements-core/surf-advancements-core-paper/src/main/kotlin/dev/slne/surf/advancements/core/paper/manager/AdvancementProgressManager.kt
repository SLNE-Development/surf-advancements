package dev.slne.surf.advancements.core.paper.manager

import dev.slne.surf.advancements.api.Advancement
import dev.slne.surf.advancements.api.progress.AdvancementProgress
import dev.slne.surf.advancements.core.common.packet.request.FetchAdvancementProgressPacket
import dev.slne.surf.advancements.core.common.packet.request.SaveAdvancementProgressPacket
import dev.slne.surf.advancements.core.paper.PaperAdvancementInstance
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object AdvancementProgressManager {
    private val _progressCache = ConcurrentHashMap.newKeySet<AdvancementProgress>()

    private val _progressQueue = ConcurrentHashMap.newKeySet<AdvancementProgress>()

    fun getProgress(playerUuid: UUID, advancement: Advancement) =
        _progressCache.firstOrNull { it.playerUuid == playerUuid && it.advancementId == advancement.id }

    fun isCompleted(playerUuid: UUID, advancement: Advancement): Boolean {
        if (advancement.requiredProgress <= 0) return false
        val progress = getProgress(playerUuid, advancement)?.progress ?: 0
        return progress >= advancement.requiredProgress
    }

    /**
     * Increments the progress for [playerUuid] on [advancement] by [amount].
     *
     * @return `true` if this increment caused the advancement to be newly completed.
     */
    fun incrementProgress(playerUuid: UUID, advancement: Advancement, amount: Int = 1): Boolean {
        if (advancement.requiredProgress <= 0) return false

        val current = getProgress(playerUuid, advancement)
        val currentProgress = current?.progress ?: 0

        if (currentProgress >= advancement.requiredProgress) {
            return false // already completed
        }

        val newProgress = (currentProgress + amount).coerceAtMost(advancement.requiredProgress)
        val updatedProgress = AdvancementProgress(
            playerUuid = playerUuid,
            advancementId = advancement.id,
            progress = newProgress,
            requiredProgress = advancement.requiredProgress
        )

        cacheProgress(updatedProgress)
        queueProgress(updatedProgress)

        return newProgress >= advancement.requiredProgress
    }

    fun cacheProgress(progress: AdvancementProgress) {
        _progressCache.removeIf { it.playerUuid == progress.playerUuid && it.advancementId == progress.advancementId }
        _progressCache.add(progress)
    }

    suspend fun fetchProgress(playerUuid: UUID) =
        PaperAdvancementInstance.rabbitApi.sendRequest(FetchAdvancementProgressPacket(playerUuid)).progresses

    fun queueProgress(progress: AdvancementProgress) = _progressQueue.add(progress)


    suspend fun flushPlayerQueue(playerUuid: UUID) {
        val toSave = _progressQueue.filter { it.playerUuid == playerUuid }
        _progressQueue.removeAll(toSave.toSet())

        toSave.forEach { saveProgress(it) }
    }

    suspend fun flushProgressQueue() {
        val toSave = _progressQueue.toList()
        _progressQueue.clear()

        toSave.forEach { saveProgress(it) }
    }

    suspend fun saveProgress(progress: AdvancementProgress) =
        PaperAdvancementInstance.rabbitApi.sendRequest(SaveAdvancementProgressPacket(progress)).value
}
