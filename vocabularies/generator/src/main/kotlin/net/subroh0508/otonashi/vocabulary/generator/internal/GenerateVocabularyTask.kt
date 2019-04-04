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

        val parentDir = parentDir(extension.outputPath)
        val namePrefix = (extension.classPrefix ?: project.name).capitalize()
        val packageName = extension.packageName ?: "${project.group}.${project.name}"

        val prefixes = extension.prefixes ?: emptyList()

        val enumFile = if (prefixes.isEmpty())
                           null
                       else
                           IriEnumGenerator(parentDir, namePrefix, packageName).execute(prefixes)

        val endpoint = extension.endpoint ?: throw IllegalArgumentException("Requires endpoint")

        val rawText = HttpClient.fetch(endpoint)

        val codeFiles = when (EndpointType.fromEndpoint(endpoint)) {
            EndpointType.TTL -> TtlCodeGenerator(parentDir, namePrefix, packageName).execute(rawText)
            EndpointType.RDF -> listOf()
        }

        val outputs = listOfNotNull(*codeFiles.toTypedArray(), enumFile)

        println("Generate [${outputs.map(File::name).joinToString(", ")}] at ${parentDir.path}!")
    }

    private fun parentDir(path: String?) = File("${project.projectDir.absolutePath}/${path ?: DEFAULT_OUTPUT_DIR}").also {
        if (it.exists()) {
            it.delete()
        }

        it.mkdirs()
    }

    companion object {
        private const val DEFAULT_OUTPUT_DIR = "build/vocabularies"
    }
}