package dev.slne.surf.advancements.api

import dev.slne.surf.advancements.api.rarity.AdvancementRarity
import dev.slne.surf.api.core.serializer.adventure.component.SerializableComponent
import dev.slne.surf.api.core.serializer.adventure.key.SerializableKey

interface Advancement {
    val id: SerializableKey
    val name: String
    val rarity: AdvancementRarity
    val requiredProgress: Int
    val hidden: Boolean
    val description: SerializableComponent
}