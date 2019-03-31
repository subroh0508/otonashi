package net.subroh0508.sparkt.triples

data class Iri(private val value: String) : TripleItem {
    override fun toString() = value
}