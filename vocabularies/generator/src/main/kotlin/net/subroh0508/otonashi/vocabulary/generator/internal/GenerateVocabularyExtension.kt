package net.subroh0508.otonashi.vocabulary.generator.internal

open class GenerateVocabularyExtension {
    var endpoint: String? = null
    var outputPath: String? = null
    var packageName: String? = null
    var classPrefix: String? = null

    internal var prefixes: List<Pair<String, String>>? = null

    fun prefix(vararg prefix: Pair<String, String>) {
        prefixes = prefix.toList()
    }
}