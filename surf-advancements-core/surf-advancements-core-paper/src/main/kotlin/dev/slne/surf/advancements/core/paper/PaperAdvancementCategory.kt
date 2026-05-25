package dev.slne.surf.advancements.core.paper

import dev.slne.surf.advancements.api.AdvancementCategory
import org.bukkit.inventory.ItemType

interface PaperAdvancementCategory : AdvancementCategory {
    val displayItem: ItemType
}