package net.subroh0508.sparkt.core

data class Prefix(
    private val name: String,
    private val iri: String
) {
    override fun toString() = "PREFIX $name: $iri"
}