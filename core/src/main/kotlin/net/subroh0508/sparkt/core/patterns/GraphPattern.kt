package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.triples.TripleItem

abstract class GraphPattern internal constructor(private val prefix: String) : Pattern, QueryItem {
    protected val patterns: MutableList<Pattern> = mutableListOf()

    infix fun TripleItem.be(pattern: TriplePattern.() -> Unit): GraphPattern {
        patterns.add(TriplePattern(this).apply(pattern))
        return this@GraphPattern
    }

    override fun toString() = buildString {
        append("$prefix { ")
        append(patterns.joinToString(" "))
        append(" }")
    }
}