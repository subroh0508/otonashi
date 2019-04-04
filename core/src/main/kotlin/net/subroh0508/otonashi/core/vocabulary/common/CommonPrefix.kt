package net.subroh0508.otonashi.core.vocabulary.common

import net.subroh0508.otonashi.triples.Prefix

enum class CommonPrefix(
    override val prefix: String,
    override val iri: String
) : Prefix {
    RDF("rdf", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"),
    RDFS("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>");

    override fun toString() = "PREFIX $prefix: $iri"
}