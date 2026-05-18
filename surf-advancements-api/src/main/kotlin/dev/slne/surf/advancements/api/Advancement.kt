package dev.slne.surf.advancements.api

import net.kyori.adventure.key.Key

interface Advancement {
    val id: Key
    val name: String
    val requiredProgress: Int
    val hidden: Boolean
    val description: String
}