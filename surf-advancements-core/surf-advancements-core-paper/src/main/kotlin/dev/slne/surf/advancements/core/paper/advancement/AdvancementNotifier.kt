package dev.slne.surf.advancements.core.paper.advancement

import dev.slne.surf.advancements.api.Advancement
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.messages.adventure.sendText
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.bukkit.Sound
import org.bukkit.entity.Player

fun notifyCompletion(player: Player, advancement: Advancement) {
    player.sendText {
        appendSuccessPrefix()
        success("Erfolg freigeschaltet: ")
        variableValue(advancement.name)
        success("!")
    }

    player.showTitle(
        Title.title(
            buildText { success("✦ Erfolg freigeschaltet!") },
            buildText { primary(advancement.name.toSmallCaps()) },
            Title.Times.times(
                Ticks.duration(10),
                Ticks.duration(50),
                Ticks.duration(20)
            )
        )
    )

    player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f)
}
