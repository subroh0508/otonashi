package net.subroh0508.sparkt.core.clauses

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.aggregates.AggregationFacade
import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.triples.TriplesStore
import net.subroh0508.sparkt.core.triples.Var

abstract class Clause internal constructor(
    private val name: String,
    private val items: List<Any>,
    store: TriplesStore
) : QueryItem, TripleFacade(store) {
    internal constructor(name: String, node: Node, store: TriplesStore) : this(name, listOf(node), store)
    internal constructor(name: String, func: AggregationFunction, store: TriplesStore) : this(name, listOf(func), store)

    class Scope(store: TriplesStore) : BinaryOperatorFacade, FunctionFacade, AggregationFacade, TripleFacade(store) {
        operator fun Var.unaryPlus() = listOf(this)
        operator fun List<Var>.plus(other: Var) = this + listOf(other)
    }

    override fun toString() = "$name ${items.joinToString(" ")}"
}
