package net.subroh0508.sparkt.core

import net.subroh0508.sparkt.core.triples.Var

internal class Select internal constructor(private val vars: List<Var>) : QueryItem {
    constructor(vararg vars: Var) : this(vars.toList())

    override fun toString() = "SELECT ${vars.joinToString(" ")}"
}