pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://reposilite.slne.dev/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("dev.slne.surf.api.gradle.settings") version "+"
}
include("surf-advancements-api")
include("surf-advancements-core")
include("surf-advancements-paper")
include("surf-advancements-microservice")
include("surf-advancements-core:surf-advancements-core-common")
include("surf-advancements-core:surf-advancements-core-paper")