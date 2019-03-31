package net.subroh0508.otonashi.core.operators.functions

import net.subroh0508.otonashi.core.operators.nodes.Node

open class Function private constructor(
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
