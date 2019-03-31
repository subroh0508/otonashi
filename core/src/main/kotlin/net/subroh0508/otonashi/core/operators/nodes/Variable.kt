package net.subroh0508.otonashi.core.operators.nodes

import net.subroh0508.otonashi.triples.Var

class Variable(private val value: Var) : Node {
    override fun toString() = value.toString()
}