package net.subroh0508.sparkt.core.aggregates

import net.subroh0508.sparkt.core.triples.TripleItem

class AggregationFunction private constructor(
    private val name: String,
    private val arguments: List<Any>
) : TripleItem {
    internal constructor(name: String, vararg arguments: Any) : this(name, arguments.toList())

    override fun toString() = buildString {
        append("$name(")
        append(arguments.joinToString(", "))
        append(")")
    }
}