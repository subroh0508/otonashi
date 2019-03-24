package net.subroh0508.sparkt.core.clauses

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.aggregates.AggregationFacade
import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.Var

abstract class Clause internal constructor(
    private val name: String,
    private val items: List<Any>
) : QueryItem {
    internal constructor(name: String, node: Node) : this(name, listOf(node))
    internal constructor(name: String, func: AggregationFunction) : this(name, listOf(func))

    object Scope : BinaryOperatorFacade, FunctionFacade, AggregationFacade {
        operator fun Var.unaryPlus() = listOf(this)
        operator fun List<Var>.plus(other: Var) = this + listOf(other)

        infix fun Node.`as`(name: String) = Var(name, this)
        infix fun AggregationFunction.`as`(name: String) = Var(name, this)
    }

    override fun toString() = "$name ${items.joinToString(" ")}"
}
