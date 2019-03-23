package net.subroh0508.sparkt.core.triples

data class IriRef(private val value: String) : TripleItem {
    override fun toString() = value
}