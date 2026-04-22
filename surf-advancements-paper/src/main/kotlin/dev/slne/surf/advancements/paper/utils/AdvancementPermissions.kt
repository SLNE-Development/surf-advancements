package dev.slne.surf.advancements.paper.utils

import dev.slne.surf.api.paper.permission.PermissionRegistry

object AdvancementPermissions : PermissionRegistry() {
    private const val PREFIX = "surf.advancements"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    val COMMAND_ADVANCEMENT = create("$COMMAND_PREFIX.advancement")
    val COMMAND_ADVANCEMENT_OTHER = create("$COMMAND_ADVANCEMENT.other")
}