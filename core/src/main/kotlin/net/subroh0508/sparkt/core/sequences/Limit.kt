package net.subroh0508.sparkt.core.sequences

import net.subroh0508.sparkt.core.QueryItem

internal class Limit internal constructor(private val n: Int): QueryItem {
    override fun toString() = "LIMIT $n"
}