package net.subroh0508.sparkt.core.scopes

import net.subroh0508.sparkt.core.QueryItem

data class OrderByScope(
    private val vars: List<QueryItem> = listOf()
) {
    override fun toString() = if (vars.isEmpty()) "" else "ORDER BY ${vars.joinToString(" ")} "
}