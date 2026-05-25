package dev.slne.surf.advancements.core.paper.advancement

import dev.slne.surf.advancements.core.paper.PaperAdvancement
import dev.slne.surf.advancements.core.paper.manager.AdvancementProgressManager
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemType

object DestroyStoneAdvancement : PaperAdvancement {
    override val id: Key = Key.key("surf-advancements", "destroy_stone")
    override val name: String = "Steinbrecher"
    override val description: String = "Zerstöre 10 Steinblöcke"
    override val requiredProgress: Int = 10
    override val hidden: Boolean = false
    override val displayItem: ItemType = ItemType.STONE
    override val listeners: ObjectSet<Listener> = ObjectOpenHashSet<Listener>().also {
        it.add(StoneBreakListener)
    }

    private object StoneBreakListener : Listener {
        @EventHandler
        fun onBlockBreak(event: BlockBreakEvent) {
            if (event.block.type != Material.STONE) return

            val player = event.player
            val newlyCompleted = AdvancementProgressManager.incrementProgress(
                player.uniqueId,
                DestroyStoneAdvancement
            )

            if (newlyCompleted) {
                notifyCompletion(player, DestroyStoneAdvancement)
            }
        }
    }
}
