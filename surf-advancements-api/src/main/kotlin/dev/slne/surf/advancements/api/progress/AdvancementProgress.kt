package dev.slne.surf.advancements.api.progress

import dev.slne.surf.api.core.serializer.adventure.key.SerializableKey
import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import kotlinx.serialization.Serializable

@Serializable
data class AdvancementProgress(
    val playerUuid: SerializableUUID,
    val advancementId: SerializableKey,
    val progress: Int,
    val requiredProgress: Int
) {
    val percentage get() = if (requiredProgress == 0) 0.0 else progress.toDouble() / requiredProgress
}
