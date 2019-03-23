package net.subroh0508.sparkt.core.sequences

import net.subroh0508.sparkt.core.QueryItem

internal class Offset internal constructor(private val n: Int): QueryItem {
    override fun toString() = "OFFSET $n"
}