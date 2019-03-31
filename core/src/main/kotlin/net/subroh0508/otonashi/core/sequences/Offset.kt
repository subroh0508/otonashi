package net.subroh0508.otonashi.core.sequences

import net.subroh0508.otonashi.core.QueryItem

internal class Offset internal constructor(private val n: Int): QueryItem {
    override fun toString() = "OFFSET $n"
}