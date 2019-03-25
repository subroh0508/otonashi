package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.triples.Var
import net.subroh0508.sparkt.core.vocabulary.Vocabulary

class GroupBy : Clause {
    internal constructor(vocabulary: Vocabulary, vars: List<Any>) : super("GROUP BY", vars, vocabulary)
    internal constructor(vocabulary: Vocabulary, vararg vars: Var) : super("GROUP BY", vars.toList(), vocabulary)
}