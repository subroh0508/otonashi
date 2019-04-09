package net.subroh0508.otonashi.core.patterns

import net.subroh0508.otonashi.core.QueryItem
import net.subroh0508.otonashi.core.operators.BinaryOperatorFacade
import net.subroh0508.otonashi.core.operators.FunctionFacade
import net.subroh0508.otonashi.core.operators.nodes.Node
import net.subroh0508.otonashi.core.triples.TripleFacadeDelegate
import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.TripleItem

abstract class GraphPattern internal constructor(
    protected val prefix: String,
    internal val vocabulary: Vocabulary
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

    fun where(where: Where.() -> Unit): Where {
        val subQuery = Where(vocabulary).apply(where)
        patterns.add(subQuery)
        return subQuery
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