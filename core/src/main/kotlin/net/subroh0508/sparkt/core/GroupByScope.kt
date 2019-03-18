package net.subroh0508.sparkt.core

data class GroupByScope(
    private var vars: List<Var> = listOf()
) {
    override fun toString() = if (vars.isEmpty()) "" else "SELECT ${vars.joinToString(" ")} "
}