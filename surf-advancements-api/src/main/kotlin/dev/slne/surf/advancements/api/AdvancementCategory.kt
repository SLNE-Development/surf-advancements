package dev.slne.surf.advancements.api

import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.key.Key

interface AdvancementCategory {
    val id: Key
    val name: String
    val members: ObjectList<Advancement>
}