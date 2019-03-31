package net.subroh0508.sparkt.core.operators.nodes

import net.subroh0508.sparkt.triples.Var

class Variable(private val value: Var) : Node {
    override fun toString() = value.toString()
}