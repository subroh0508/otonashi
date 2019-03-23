package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.triples.Var

class GroupBy internal constructor(private val vars: List<Var>) : QueryItem {
    constructor(vararg vars: Var) : this(vars.toList())

    override fun toString() = "GROUP BY ${vars.joinToString(" ")}"
}