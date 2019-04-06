package net.subroh0508.otonashi.core.serializer.rdfelement

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.subroh0508.otonashi.core.serializer.type.LiteralWithDataTypeCastDelegate
import java.net.URI

internal sealed class RDFElement {
    companion object {
        const val TYPE_URI = "uri"
        const val TYPE_LITERAL = "literal"
        const val TYPE_BNODE = "bnode"

        const val LANGUAGE_TAG = "xml:lang"
        const val DATATYPE_IRI = "datatype"
    }

    abstract val castedValue: Any

    @Serializable
    data class IRI(
        val type: String,
        val value: String
    ) : RDFElement() {
        @Transient
        override val castedValue = URI(value)
    }

    @Serializable
    data class Literal(
        val type: String,
        val value: String
    ) : RDFElement() {
        @Transient
        override val castedValue = value
    }

    @Serializable
    data class LiteralWithLang(
        val type: String,
        val value: String,
        @SerialName(LANGUAGE_TAG)
        val lang: String
    ) : RDFElement() {
        @Transient
        override val castedValue = value
    }

    @Serializable
    data class BlankNode(
        val type: String,
        val value: String
    ) : RDFElement() {
        @Transient
        override val castedValue = value
    }

    @Serializable
    data class LiteralWithDataType(
        val type: String,
        val value: String,
        val datatype: String
    ) : RDFElement() {
        @Transient
        override val castedValue: Any by LiteralWithDataTypeCastDelegate
    }
}