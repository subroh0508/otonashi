package net.subroh0508.sparkt.core

import net.subroh0508.sparkt.core.aggregates.AggregationFacade
import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TripleItem
import net.subroh0508.sparkt.core.triples.Var

class Select internal constructor(private val vars: List<TripleItem>) : QueryItem {
    object Scope : BinaryOperatorFacade, FunctionFacade, AggregationFacade {
        fun Node.asVar(name: String) = AsVar(this, name)
        fun AggregationFunction.asVar(name: String) = AsVar(this, name)

        operator fun TripleItem.unaryPlus() = listOf(this)
        operator fun TripleItem.plus(other: TripleItem) = listOf(this, other)
        operator fun List<TripleItem>.plus(other: TripleItem) = this + listOf(other)

        data class AsVar internal constructor(
            private val func: Any,
            private val value: Var
        ) : TripleItem {
            constructor(func: Any, name: String) : this(func, Var(name))

            override fun toString() = buildString {
                append("(")
                append("$func as $value")
                append(")")
            }
        }
    }

    internal constructor(vararg vars: Var) : this(vars.toList())

    override fun toString() = "SELECT ${vars.joinToString(" ")}"
}