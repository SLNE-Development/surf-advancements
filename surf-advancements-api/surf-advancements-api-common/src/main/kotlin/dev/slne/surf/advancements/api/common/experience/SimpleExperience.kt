package dev.slne.surf.advancements.api.common.experience

import dev.slne.surf.api.core.serializer.java.uuid.SerializableUUID
import kotlinx.serialization.Serializable

@Serializable
data class SimpleExperience(
    val uuid: SerializableUUID,
    val advancementName: String,
    val currentExperience: Int
)
