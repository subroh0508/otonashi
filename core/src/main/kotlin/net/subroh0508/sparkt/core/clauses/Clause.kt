package net.subroh0508.sparkt.core.clauses

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.aggregates.AggregationFacade
import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TripleFacadeDelegate
import net.subroh0508.sparkt.core.vocabulary.Vocabulary
import net.subroh0508.sparkt.triples.TripleFacade
import net.subroh0508.sparkt.triples.Var

abstract class Clause internal constructor(
    private val name: String,
    private val items: List<Any>,
    vocabulary: Vocabulary
) : QueryItem, TripleFacade by TripleFacadeDelegate(vocabulary) {
    internal constructor(name: String, node: Node, vocabulary: Vocabulary) : this(name, listOf(node), vocabulary)
    internal constructor(name: String, func: AggregationFunction, vocabulary: Vocabulary) : this(name, listOf(func), vocabulary)

    class Scope internal constructor(
        vocabulary: Vocabulary
    ) : BinaryOperatorFacade,
        FunctionFacade,
        AggregationFacade,
        TripleFacade by TripleFacadeDelegate(vocabulary) {
        operator fun Var.unaryPlus() = listOf(this)
        operator fun List<Var>.plus(other: Var) = this + listOf(other)
    }

    override fun toString() = "$name ${items.joinToString(" ")}"
}
