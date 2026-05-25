package dev.slne.surf.advancements.core.paper

import dev.slne.surf.advancements.api.Advancement
import dev.slne.surf.advancements.api.rarity.AdvancementRarity
import io.papermc.paper.advancement.AdvancementDisplay
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemType

interface PaperAdvancement : Advancement {
    val displayItem: ItemType
    val listeners: ObjectSet<Listener>

    fun frameType() = when (rarity) {
        AdvancementRarity.NORMAL -> AdvancementDisplay.Frame.TASK
        AdvancementRarity.RARE -> AdvancementDisplay.Frame.TASK
        AdvancementRarity.EPIC -> AdvancementDisplay.Frame.CHALLENGE
        AdvancementRarity.LEGENDARY -> AdvancementDisplay.Frame.GOAL
    }
}