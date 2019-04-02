package net.subroh0508.otonashi.vocabulary.generator.internal

import net.subroh0508.otonashi.vocabulary.generator.OtonashiVocabularyGeneratorPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class GenerateVocabularyTask : DefaultTask() {
    @TaskAction
    fun action() {
        val extension = project.extensions.run {
            findByName(OtonashiVocabularyGeneratorPlugin.GENERATOR_PLUGIN_NAME) as GenerateVocabularyExtension
        }

        println(extension.endpoint)
        println(extension.prefix)
        println(extension.prefixIri)
    }
}