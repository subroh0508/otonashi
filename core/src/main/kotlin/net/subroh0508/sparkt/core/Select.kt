package net.subroh0508.sparkt.core

import net.subroh0508.sparkt.core.operators.BinaryOperatorFacade
import net.subroh0508.sparkt.core.operators.FunctionFacade
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TripleItem
import net.subroh0508.sparkt.core.triples.Var

class Select internal constructor(private val vars: List<TripleItem>) : QueryItem {
    object Scope : BinaryOperatorFacade, FunctionFacade {
        fun Node.asVar(name: String) = AsVar(this, name)

        data class AsVar internal constructor(
            private val node: Node,
            private val value: Var
        ) : TripleItem {
            constructor(node: Node, name: String) : this(node, Var(name))

            override fun toString() = buildString {
                append("(")
                append("$node as $value")
                append(")")
            }
        }
    }

    internal constructor(vararg vars: Var) : this(vars.toList())

    override fun toString() = "SELECT ${vars.joinToString(" ")}"
}