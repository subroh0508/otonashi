package net.subroh0508.sparkt.core.order

import net.subroh0508.sparkt.triples.TripleItem
import net.subroh0508.sparkt.triples.Var

data class Desc(private val value: Var) : TripleItem {
    override fun toString() = "DESC($value)"
}