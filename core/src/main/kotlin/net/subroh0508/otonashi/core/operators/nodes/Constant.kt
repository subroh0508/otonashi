package net.subroh0508.otonashi.core.operators.nodes

class Constant(private val value: Any) : Node {
    override fun toString() = when (value) {
        is Number -> value.toString()
        else -> "'$value'"
    }
}