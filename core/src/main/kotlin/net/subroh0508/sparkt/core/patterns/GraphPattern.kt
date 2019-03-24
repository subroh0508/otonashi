package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.triples.TripleItem
import net.subroh0508.sparkt.core.triples.TriplesStore

abstract class GraphPattern internal constructor(
    protected val prefix: String,
    store: TriplesStore
) : Pattern, QueryItem, TripleFacade(store) {
    class Scope(store: TriplesStore) : BinaryOperatorFacade, FunctionFacade, TripleFacade(store)

    protected val patterns: MutableList<Pattern> = mutableListOf()

    infix fun TripleItem.be(pattern: TriplePattern.() -> Unit): GraphPattern {
        patterns.add(TriplePattern(this, store).apply(pattern))
        return this@GraphPattern
    }

    fun optional(optional: Optional.() -> Unit): GraphPattern {
        patterns.add(Optional(store).apply(optional))
        return this
    }

    fun filter(filter: GraphPattern.Scope.() -> Node): GraphPattern {
        patterns.add(Filter(filter(GraphPattern.Scope(store)), store))
        return this
    }

    override fun toString() = buildString {
        append("$prefix { ")
        append(patterns.joinToString(" "))
        append(" }")
    }
}