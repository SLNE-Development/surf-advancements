package dev.slne.surf.advancements.core.paper.advancement

import dev.slne.surf.advancements.api.rarity.AdvancementRarity
import dev.slne.surf.advancements.core.paper.PaperAdvancement
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import dev.slne.surf.api.core.messages.adventure.buildText
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTameEvent
import org.bukkit.inventory.ItemType

object TameAnimalAdvancement : PaperAdvancement {
    override val id: Key = Key.key("surf-advancements", "tame_animal")
    override val name: String = "Tierfreund"
    override val description = buildText {
        spacer("Zähme ein Tier")
    }
    override val requiredProgress: Int = 1
    override val rarity = AdvancementRarity.LEGENDARY
    override val hidden: Boolean = false
    override val displayItem: ItemType = ItemType.BONE
    override val listeners: ObjectSet<Listener> = ObjectOpenHashSet<Listener>().also {
        it.add(EntityTameListener)
    }

    private object EntityTameListener : Listener {
        @EventHandler
        fun onEntityTame(event: EntityTameEvent) {
            val player = event.owner as? Player ?: return

            val newlyCompleted = AdvancementProgressManager.incrementProgress(
                player.uniqueId,
                TameAnimalAdvancement
            )

            if (newlyCompleted) {
                notifyCompletion(player, TameAnimalAdvancement)
            }
        }
    }
}
