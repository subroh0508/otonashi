package net.subroh0508.otonashi.vocabularies.foaf

import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

object Foaf : IriVocabulary(
    "foaf", "age"
) {
    object Prefix : net.subroh0508.otonashi.triples.Prefix {
        override val prefix = "foaf"
        override val iri = "<http://xmlns.com/foaf/0.1/>"

        override fun toString() = "PREFIX $prefix: $iri"
    }

    val age by store
}