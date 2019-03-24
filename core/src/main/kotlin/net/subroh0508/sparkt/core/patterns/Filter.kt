package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.triples.TriplesStore

class Filter(private val node: Node? = null, store: TriplesStore) : GraphPattern("FILTER", store) {
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