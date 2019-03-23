package net.subroh0508.sparkt.core.triples

data class Var(private val value: String) : TripleItem {
    override fun toString() = "?$value"
}