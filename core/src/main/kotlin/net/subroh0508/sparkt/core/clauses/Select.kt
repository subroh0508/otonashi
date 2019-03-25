package net.subroh0508.sparkt.core.clauses

import net.subroh0508.sparkt.core.triples.Var
import net.subroh0508.sparkt.core.vocabulary.Vocabulary

class Select : Clause {
    internal constructor(vocabulary: Vocabulary, vars: List<Any>) : super("SELECT", vars, vocabulary)
    internal constructor(vocabulary: Vocabulary, vararg vars: Var) : super("SELECT", vars.toList(), vocabulary)
}