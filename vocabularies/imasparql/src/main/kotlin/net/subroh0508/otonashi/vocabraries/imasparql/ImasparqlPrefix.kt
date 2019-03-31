package net.subroh0508.otonashi.vocabraries.imasparql

import net.subroh0508.otonashi.triples.Prefix

enum class ImasparqlPrefix(
    override val prefix: String,
    override val iri: String
) : Prefix {
    IMAS("imas", "<https://sparql.crssnky.xyz/imasrdf/URIs/imas-schema.ttl#>"),
    IMAS_RDF("imasrdf", "<https://sparql.crssnky.xyz/imasrdf/RDFs/detail/>");

    override fun toString() = "PREFIX $prefix: $iri"
}