package net.subroh0508.sparkt.core.scopes

import net.subroh0508.sparkt.core.Var

data class GroupByScope(
    private var vars: List<Var> = listOf()
) {
    override fun toString() = if (vars.isEmpty()) "" else "SELECT ${vars.joinToString(" ")} "
}