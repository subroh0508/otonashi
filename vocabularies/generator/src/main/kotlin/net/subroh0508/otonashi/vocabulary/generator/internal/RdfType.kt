package net.subroh0508.otonashi.vocabulary.generator.internal

internal enum class RdfType(private val className: String) {
    CLASS("Class"), PROPERTY("Property");

    fun className(prefix: String) = prefix + className

    companion object {
        fun fromRawText(text: String) = when {
            text.contains("rdfs:Class") -> CLASS
            text.contains("rdf:Property") -> PROPERTY
            else -> null
        }
    }
}