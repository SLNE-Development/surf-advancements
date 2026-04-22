package dev.slne.surf.advancements.api.paper.advancements

import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.api.core.util.requiredService

private val impl = requiredService<MiningAdvancement>()

interface MiningAdvancement : Advancement {
    companion object : MiningAdvancement by impl
}

