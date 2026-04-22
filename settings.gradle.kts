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


include("surf-advancements-api:surf-advancements-api-paper")
include("surf-advancements-api:surf-advancements-api-common")
include("surf-advancements-core:surf-advancements-core-common")
include("surf-advancements-core:surf-advancements-core-paper")
include("surf-advancements-microservice")
include("surf-advancements-paper")