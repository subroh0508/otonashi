package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.operators.nodes.Node

class AggregationFunction private constructor(
    private val name: String,
    private val arguments: List<Any>
) : Node {
    internal constructor(name: String, vararg arguments: Any) : this(name, arguments.toList())

    override fun toString() = buildString {
        append("$name(")
        append(arguments.joinToString(", "))
        append(")")
    }
}