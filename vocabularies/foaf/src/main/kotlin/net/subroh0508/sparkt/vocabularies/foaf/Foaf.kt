package net.subroh0508.sparkt.vocabularies.foaf

import net.subroh0508.sparkt.triples.vocabulary.IriVocabulary

object Foaf : IriVocabulary(
    "foaf", "age"
) {
    object Prefix : net.subroh0508.sparkt.triples.Prefix {
        override val prefix = "foaf"
        override val iri = "<http://xmlns.com/foaf/0.1/>"

        override fun toString() = "PREFIX $prefix: $iri"
    }

    val age by store
}