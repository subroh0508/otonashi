package net.subroh0508.otonashi.core.sequences

import net.subroh0508.otonashi.core.QueryItem
import net.subroh0508.otonashi.triples.Var

internal class OrderBy internal constructor(private val vars: List<Var>) : QueryItem {
    constructor(vararg vars: Var) : this(vars.toList())

    override fun toString() = "SELECT ${vars.joinToString(" ")}"
}