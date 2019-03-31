package net.subroh0508.otonashi.vocabraries.schema

import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

object Schema : IriVocabulary(
    "schema", "name", "memberOf"
) {
    object Prefix : net.subroh0508.otonashi.triples.Prefix {
        override val prefix = "schema"
        override val iri = "<http://schema.org/>"

        override fun toString() = "PREFIX $prefix: $iri"
    }

    val name by store
    val memberOf by store
}
