package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.Var

class Having : Clause {
    internal constructor(vars: List<Any>) : super("HAVING", vars)
    internal constructor(node: Node) : super("HAVING", node)
    internal constructor(vararg vars: Var) : super("HAVING", vars.toList())
}