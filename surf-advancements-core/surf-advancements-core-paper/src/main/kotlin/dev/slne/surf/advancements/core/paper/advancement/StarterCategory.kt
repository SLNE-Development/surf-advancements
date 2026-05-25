package dev.slne.surf.advancements.core.paper.advancement

import dev.slne.surf.advancements.api.Advancement
import dev.slne.surf.advancements.core.paper.PaperAdvancementCategory
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectList
import net.kyori.adventure.key.Key
import org.bukkit.inventory.ItemType

object StarterCategory : PaperAdvancementCategory {
    override val id: Key = Key.key("surf-advancements", "starter")
    override val name: String = "Einsteiger"
    override val displayItem: ItemType = ItemType.GRASS_BLOCK
    override val members: ObjectList<Advancement> = ObjectArrayList<Advancement>().also {
        it.add(DestroyStoneAdvancement)
        it.add(TameAnimalAdvancement)
    }
}
