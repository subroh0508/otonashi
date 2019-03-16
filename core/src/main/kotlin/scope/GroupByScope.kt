package net.subroh0508.core.scope

import net.subroh0508.core.Var

data class GroupByScope(
    private var vars: List<Var> = listOf()
) {
    override fun toString() = if (vars.isEmpty()) "" else "SELECT ${vars.joinToString(" ")} "
}