package net.subroh0508.otonashi.core.sequences

import net.subroh0508.otonashi.core.QueryItem

internal class Limit internal constructor(private val n: Int): QueryItem {
    override fun toString() = "LIMIT $n"
}