package dev.slne.surf.advancements.core.paper

import dev.slne.surf.advancements.api.Advancement
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemType

interface PaperAdvancement : Advancement {
    val displayItem: ItemType
    val listeners: ObjectSet<Listener>
}