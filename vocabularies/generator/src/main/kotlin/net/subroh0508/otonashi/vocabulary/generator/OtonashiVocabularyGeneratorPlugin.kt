package net.subroh0508.otonashi.vocabulary.generator

import net.subroh0508.otonashi.vocabulary.generator.internal.GenerateVocabularyExtension
import net.subroh0508.otonashi.vocabulary.generator.internal.GenerateVocabularyTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class OtonashiVocabularyGeneratorPlugin : Plugin<Project> {
    companion object {
        internal const val GENERATOR_PLUGIN_NAME = "generateVocabulary"
    }

    override fun apply(target: Project) {
        target.extensions.run {
            create(GENERATOR_PLUGIN_NAME, GenerateVocabularyExtension::class.java)
        }

        with (target.tasks) {
            create(GENERATOR_PLUGIN_NAME, GenerateVocabularyTask::class.java) {
                group = "Generation Tasks"
                description = "Generates SPARQL vocabulary class"
            }
        }
    }
}