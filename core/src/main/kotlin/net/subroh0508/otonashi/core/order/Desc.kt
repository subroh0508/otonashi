package net.subroh0508.otonashi.core.order

import net.subroh0508.otonashi.triples.TripleItem
import net.subroh0508.otonashi.triples.Var

data class Desc(private val value: Var) : TripleItem {
    override fun toString() = "DESC($value)"
}