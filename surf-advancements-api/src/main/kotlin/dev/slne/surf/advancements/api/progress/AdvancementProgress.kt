package dev.slne.surf.advancements.api.progress

import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import kotlinx.serialization.Serializable
import net.kyori.adventure.key.Key

@Serializable
data class AdvancementProgress(
    val playerUuid: SerializableUUID,
    val advancementId: Key,
    val progress: Int,
    val requiredProgress: Int
) {
    val percentage get() = if (requiredProgress == 0) 0.0 else progress.toDouble() / requiredProgress
}
