package dev.slne.surf.advancements.core.paper.advancements

import com.google.auto.service.AutoService
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.util.objectListOf
import dev.slne.surf.advancements.api.paper.advancements.ExampleAdvancement
import dev.slne.surf.advancements.api.paper.level.AdvancementLevel
import dev.slne.surf.advancements.api.paper.level.reward.rewards.LevelItemRewards
import dev.slne.surf.advancements.core.paper.AbstractAdvancement
import dev.slne.surf.advancements.core.paper.level.advancementLevel
import it.unimi.dsi.fastutil.objects.ObjectList
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ItemType

@AutoService(ExampleAdvancement::class)
object ExampleAdvancementImpl : AbstractAdvancement(
    name = "mining",
    material = ItemType.IRON_PICKAXE,
    displayName = buildText { primary("Bergbau") },
    lore = {
        line { spacer("Baue Blöcke ab, um XP zu verdienen.") }
    },
), ExampleAdvancement {

    override fun getExtraLevels(): ObjectList<AdvancementLevel> = objectListOf(

        // Level 1 — Einsteiger (kein XP benötigt)
        advancementLevel(
            advancement = this,
            level = 1,
            requiredExperience = 0,
            description = { line { spacer("Fang an zu graben!") } }
        ),

        // Level 2 — Lehrling: 250 XP → 5× Iron Ingot
        advancementLevel(
            advancement = this,
            level = 2,
            requiredExperience = 250,
            description = { line { spacer("Du kennst die Grundlagen.") } },
            rewards = {
                add(LevelItemRewards(objectListOf(ItemStack.of(Material.IRON_INGOT, 5))))
            }
        ),

        // Level 3 — Geselle: 750 XP → 3× Gold Ingot + 1× Iron Pickaxe
        advancementLevel(
            advancement = this,
            level = 3,
            requiredExperience = 750,
            description = { line { spacer("Der Fels weicht dir.") } },
            rewards = {
                add(
                    LevelItemRewards(
                        objectListOf(
                            ItemStack.of(Material.GOLD_INGOT, 3),
                            ItemStack.of(Material.IRON_PICKAXE)
                        )
                    )
                )
            }
        ),

        // Level 4 — Experte: 2000 XP → 2× Diamond
        advancementLevel(
            advancement = this,
            level = 4,
            requiredExperience = 2_000,
            description = { line { spacer("Diamanten schimmern im Dunkel.") } },
            rewards = {
                add(LevelItemRewards(objectListOf(ItemStack.of(Material.DIAMOND, 2))))
            }
        ),

        // Level 5 — Meister: 5000 XP → 1× Netherite Ingot + 5× Diamond + 1× Diamond Pickaxe
        advancementLevel(
            advancement = this,
            level = 5,
            requiredExperience = 5_000,
            description = { line { spacer("Der ultimative Bergmann.") } },
            rewards = {
                add(
                    LevelItemRewards(
                        objectListOf(
                            ItemStack.of(Material.NETHERITE_INGOT),
                            ItemStack.of(Material.DIAMOND, 5),
                            ItemStack.of(Material.DIAMOND_PICKAXE)
                        )
                    )
                )
            }
        )
    )
}
