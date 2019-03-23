package net.subroh0508.sparkt.core.operators.nodes

class Constant(private val value: Any) : Node {
    override fun toString() = when (value) {
        is Number -> value.toString()
        else -> "'$value'"
    }
}