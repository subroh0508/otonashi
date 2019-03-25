package net.subroh0508.sparkt.core.vocabulary.common

import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

object Rdf : IriVocabulary(
    "rdf", "type"
) {
    object Prefix : net.subroh0508.sparkt.core.Prefix {
        override val prefix = "rdf"
        override val iri = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

        override fun toString() = "PREFIX $prefix: $iri"
    }

    val type by store
}