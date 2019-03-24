package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.triples.Var

class GroupBy : Clause {
    internal constructor(vars: List<Any>) : super("GROUP BY", vars)
    internal constructor(vararg vars: Var) : super("GROUP BY", vars.toList())
}