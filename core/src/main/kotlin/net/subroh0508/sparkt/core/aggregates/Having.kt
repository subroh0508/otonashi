package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TriplesStore
import net.subroh0508.sparkt.core.triples.Var

class Having : Clause {
    internal constructor(store: TriplesStore, vars: List<Any>) : super("HAVING", vars, store)
    internal constructor(store: TriplesStore, node: Node) : super("HAVING", node, store)
    internal constructor(store: TriplesStore, vararg vars: Var) : super("HAVING", vars.toList(), store)
}