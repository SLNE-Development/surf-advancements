package dev.slne.surf.advancements.api.paper.advancements

import dev.slne.surf.advancements.api.paper.Advancement
import dev.slne.surf.api.core.util.requiredService

private val impl = requiredService<ExampleAdvancement>()

interface ExampleAdvancement : Advancement {
    companion object : ExampleAdvancement by impl
}

