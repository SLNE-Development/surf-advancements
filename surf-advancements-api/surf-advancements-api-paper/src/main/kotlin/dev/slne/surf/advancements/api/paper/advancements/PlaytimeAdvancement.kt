package dev.slne.surf.advancements.api.paper.advancements

import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.api.core.util.requiredService

private val impl = requiredService<PlaytimeAdvancement>()

interface PlaytimeAdvancement : Advancement {
    companion object : PlaytimeAdvancement by impl
}