import dev.slne.surf.api.gradle.util.registerRequired
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

dependencies {
    api(projects.surfAdvancementsCore.surfAdvancementsCorePaper)
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.advancements.paper.PaperMain")
    foliaSupported(true)
    generateLibraryLoader(false)
    authors.addAll("red")
}