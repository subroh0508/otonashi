package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node

class Filter(private val node: Node? = null) : GraphPattern("FILTER") {
    object Scope : BinaryOperatorFacade, FunctionFacade

    override fun toString()
            = if (node != null) {
                buildString {
                    append("$prefix(")
                    append(node.toString())
                    append(")")
                }
            } else {
                super.toString()
            }
}