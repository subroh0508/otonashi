package net.subroh0508.otonashi.core.aggregates

import net.subroh0508.otonashi.core.clauses.Clause
import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.triples.Var

class GroupBy : Clause {
    internal constructor(vocabulary: Vocabulary, vars: List<Any>) : super("GROUP BY", vars, vocabulary)
    internal constructor(vocabulary: Vocabulary, vararg vars: Var) : super("GROUP BY", vars.toList(), vocabulary)
}