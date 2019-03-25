package net.subroh0508.sparkt.core

enum class CommonPrefix(
    override val prefix: String,
    override val iri: String
) : Prefix {
    SCHEMA("schema", "<http://schema.org/>"),
    RDF("rdf", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"),
    FOAF("foaf", "<http://xmlns.com/foaf/0.1/>"),
    MATH("math", "<http://www.w3.org/2005/xpath-functions/math#>"),
    XSD("xsd", "<https://www.w3.org/TR/xmlschema11-2/#>"),
    RDFS("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>");

    override fun toString() = "PREFIX $prefix: $iri"
}