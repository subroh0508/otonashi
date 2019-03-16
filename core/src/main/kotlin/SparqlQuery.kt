package net.subroh0508.core

import net.subroh0508.core.scope.GroupByScope
import net.subroh0508.core.scope.OrderByScope
import net.subroh0508.core.scope.WhereScope
import java.net.URLEncoder

class SparqlQuery(
    private val endpoint: String,
    private val prefixes: List<Prefix>
) {
    private val whereScope: WhereScope by lazy(::WhereScope)
    private var selectVars: List<Var> = listOf(Var("*"))
    private var groupByScope: GroupByScope = GroupByScope()
    private var orderByScope: OrderByScope = OrderByScope()
    private var offset: Int? = null
    private var limit: Int? = null

    fun where(scope: WhereScope.() -> Unit): SparqlQuery {
        whereScope.apply(scope)
        return this
    }

    fun select(vararg vars: Var): SparqlQuery {
        selectVars = vars.toList()
        return this
    }

    fun groupBy(vararg vars: Var): SparqlQuery {
        groupByScope = GroupByScope(vars.toList())
        return this
    }

    fun orderBy(vararg vars: QueryItem): SparqlQuery {
        orderByScope = OrderByScope(vars.toList())
        return this
    }

    fun limit(n: Int): SparqlQuery {
        limit = n
        return this
    }

    fun offset(n: Int): SparqlQuery {
        offset = n
        return this
    }

    override fun toString() = "$endpoint?force-accept=text%2Fplain&query=${buildQuery()}"

    private fun buildQuery(): String {
        val query = prefixes.joinToString(" ") +
                "SELECT ${selectVars.joinToString(" ")} " +
                whereScope.toString() +
                groupByScope.toString() +
                orderByScope.toString() +
                (offset?.let { "OFFSET $it" } ?: "")+
                (limit?.let { "LIMIT $it" } ?: "")

        return URLEncoder.encode(query, "UTF-8")
    }
}