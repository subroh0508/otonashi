package net.subroh0508.core.order

import net.subroh0508.core.QueryItem
import net.subroh0508.core.Var

data class Desc(private val value: Var) : QueryItem {
    override fun toString() = "DESC($value)"
}