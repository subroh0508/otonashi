package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TripleFacadeDelegate
import net.subroh0508.sparkt.core.vocabulary.Vocabulary
import net.subroh0508.sparkt.triples.TripleFacade
import net.subroh0508.sparkt.triples.TripleItem

abstract class GraphPattern internal constructor(
    protected val prefix: String,
    private val vocabulary: Vocabulary
) : Pattern, QueryItem, TripleFacade by TripleFacadeDelegate(vocabulary) {
    class Scope internal constructor(
        vocabulary: Vocabulary
    ) : BinaryOperatorFacade,
        FunctionFacade,
        TripleFacade by TripleFacadeDelegate(vocabulary)

    protected val patterns: MutableList<Pattern> = mutableListOf()

    infix fun TripleItem.be(pattern: TriplePattern.() -> Unit): GraphPattern {
        patterns.add(TriplePattern(this, vocabulary).apply(pattern))
        return this@GraphPattern
    }

    fun optional(optional: Optional.() -> Unit): GraphPattern {
        patterns.add(Optional(vocabulary).apply(optional))
        return this
    }

    fun filter(filter: GraphPattern.Scope.() -> Node): GraphPattern {
        patterns.add(Filter(filter(GraphPattern.Scope(vocabulary)), vocabulary))
        return this
    }

    override fun toString() = buildString {
        append("$prefix { ")
        append(patterns.joinToString(" "))
        append(" }")
    }
}