package net.subroh0508.otonashi.core.clauses

import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.triples.Var

class Select : Clause {
    internal constructor(vocabulary: Vocabulary, vars: List<Any>) : super("SELECT", vars, vocabulary)
    internal constructor(vocabulary: Vocabulary, vararg vars: Var) : super("SELECT", vars.toList(), vocabulary)
}