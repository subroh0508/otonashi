package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.triples.TriplesStore
import net.subroh0508.sparkt.core.triples.Var

class GroupBy : Clause {
    internal constructor(store: TriplesStore, vars: List<Any>) : super("GROUP BY", vars, store)
    internal constructor(store: TriplesStore, vararg vars: Var) : super("GROUP BY", vars.toList(), store)
}