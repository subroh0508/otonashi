rootProject.name = "otonashi"

include(":core",
        "triples",
        ":vocabularies:generator",
        ":vocabularies:schema",
        ":vocabularies:foaf",
        ":vocabularies:imasparql",
        ":sample:androidapp",
        ":sample:ktorreact")

pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenLocal()
        maven("https://kotlin.bintray.com/kotlinx")
    }

    resolutionStrategy {
        eachPlugin {
            when {
                requested.id.id.startsWith("com.android") -> useModule(Dep.GradlePlugin.android)
                requested.id.id.startsWith("org.jetbrains.kotlin") -> useModule(Dep.GradlePlugin.kotlin)
                requested.id.id == "kotlinx-serialization" -> useModule(Dep.GradlePlugin.kotlinSerialization)
            }
        }
    }
}
