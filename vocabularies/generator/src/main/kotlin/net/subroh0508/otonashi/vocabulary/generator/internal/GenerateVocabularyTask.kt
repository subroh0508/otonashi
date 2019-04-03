package net.subroh0508.otonashi.vocabulary.generator.internal

import net.subroh0508.otonashi.vocabulary.generator.OtonashiVocabularyGeneratorPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateVocabularyTask : DefaultTask() {
    @TaskAction
    fun action() {
        val extension = project.extensions.run {
            findByName(OtonashiVocabularyGeneratorPlugin.GENERATOR_PLUGIN_NAME) as GenerateVocabularyExtension
        }

        val endpoint = extension.endpoint ?: throw IllegalArgumentException("Requires endpoint")
        val packageName = extension.packageName ?: "${project.group}.${project.name}"
        val classNamePrefix = (extension.classPrefix ?: project.name).capitalize()

        val rawText = HttpClient.fetch(endpoint)
        val parentDir = parentDir(extension.outputPath)

        val files = when (EndpointType.fromEndpoint(endpoint)) {
            EndpointType.TTL -> TtlCodeGenerator(parentDir, classNamePrefix, packageName).execute(rawText)
            EndpointType.RDF -> listOf()
        }

        println("Generate [${files.map(File::name).joinToString(", ")}] at ${parentDir.path}!")
    }

    private fun parentDir(path: String?) = File("${project.path}/${path ?: DEFAULT_OUTPUT_DIR}").also {
        if (it.exists()) {
            it.delete()
        }

        it.mkdirs()
    }

    companion object {
        private const val DEFAULT_OUTPUT_DIR = "build/vocabularies"
    }
}