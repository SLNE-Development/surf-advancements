package dev.slne.surf.advancements.api

import dev.slne.surf.api.core.serializer.adventure.component.SerializableComponent
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.key.Key

interface AdvancementCategory {
    val id: Key
    val name: String
    val description: SerializableComponent
    val members: ObjectList<Advancement>
}