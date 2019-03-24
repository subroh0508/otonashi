package net.subroh0508.sparkt.core.clauses

import net.subroh0508.sparkt.core.triples.Var

class Select : Clause {
    internal constructor(vars: List<Any>) : super("SELECT", vars)
    internal constructor(vararg vars: Var) : super("SELECT", vars.toList())
}