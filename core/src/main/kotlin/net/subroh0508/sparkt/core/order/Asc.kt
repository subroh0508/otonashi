package net.subroh0508.core.order

import net.subroh0508.sparkt.core.QueryItem
import net.subroh0508.sparkt.core.Var

data class Asc(private val value: Var) : QueryItem {
    override fun toString(): String = "ASC($value)"
}