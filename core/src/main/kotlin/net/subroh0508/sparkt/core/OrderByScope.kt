package net.subroh0508.sparkt.core

data class OrderByScope(
    private val vars: List<QueryItem> = listOf()
) {
    override fun toString() = if (vars.isEmpty()) "" else "ORDER BY ${vars.joinToString(" ")} "
}