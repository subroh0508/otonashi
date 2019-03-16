package net.subroh0508.core.scope

import net.subroh0508.core.QueryItem

data class OrderByScope(
    private val vars: List<QueryItem> = listOf()
) {
    override fun toString() = if (vars.isEmpty()) "" else "ORDER BY ${vars.joinToString(" ")} "
}