package net.subroh0508.sparkt.core.clauses

import net.subroh0508.sparkt.core.triples.TriplesStore
import net.subroh0508.sparkt.core.triples.Var

class Select : Clause {
    internal constructor(store: TriplesStore, vars: List<Any>) : super("SELECT", vars, store)
    internal constructor(store: TriplesStore, vararg vars: Var) : super("SELECT", vars.toList(), store)
}