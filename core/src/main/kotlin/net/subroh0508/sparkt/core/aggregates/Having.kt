package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.Var
import net.subroh0508.sparkt.core.vocabulary.Vocabulary

class Having : Clause {
    internal constructor(vocabulary: Vocabulary, vars: List<Any>) : super("HAVING", vars, vocabulary)
    internal constructor(vocabulary: Vocabulary, node: Node) : super("HAVING", node, vocabulary)
    internal constructor(vocabulary: Vocabulary, vararg vars: Var) : super("HAVING", vars.toList(), vocabulary)
}