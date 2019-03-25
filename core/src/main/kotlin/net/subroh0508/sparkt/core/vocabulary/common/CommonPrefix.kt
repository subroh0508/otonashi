package net.subroh0508.sparkt.core.vocabulary.common

import net.subroh0508.sparkt.core.Prefix

enum class CommonPrefix(
    override val prefix: String,
    override val iri: String
) : Prefix {
    MATH("math", "<http://www.w3.org/2005/xpath-functions/math#>"),
    XSD("xsd", "<https://www.w3.org/TR/xmlschema11-2/#>"),
    RDFS("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>");

    override fun toString() = "PREFIX $prefix: $iri"
}