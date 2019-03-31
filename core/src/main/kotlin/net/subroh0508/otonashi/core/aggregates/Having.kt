package net.subroh0508.otonashi.core.aggregates

import net.subroh0508.otonashi.core.clauses.Clause
import net.subroh0508.otonashi.core.operators.nodes.Node
import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.triples.Var

class Having : Clause {
    internal constructor(vocabulary: Vocabulary, vars: List<Any>) : super("HAVING", vars, vocabulary)
    internal constructor(vocabulary: Vocabulary, node: Node) : super("HAVING", node, vocabulary)
    internal constructor(vocabulary: Vocabulary, vararg vars: Var) : super("HAVING", vars.toList(), vocabulary)
}