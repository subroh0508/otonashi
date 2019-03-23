package net.subroh0508.sparkt.core

import net.subroh0508.sparkt.core.aggregates.GroupBy
import net.subroh0508.sparkt.core.patterns.Where
import net.subroh0508.sparkt.core.sequences.Limit
import net.subroh0508.sparkt.core.sequences.Offset
import net.subroh0508.sparkt.core.sequences.OrderBy
import net.subroh0508.sparkt.core.triples.TripleItem
import net.subroh0508.sparkt.core.triples.Var
import java.net.URLEncoder

class SparqlQuery(
    private val endpoint: String,
    private val prefixes: List<Prefix>
) {
    private var select: Select = Select(Var("*"))
    private val where: Where by lazy(::Where)
    private var groupBy: GroupBy? = null
    private var orderBy: OrderBy? = null
    private var limit: Limit? = null
    private var offset: Offset? = null

    fun where(scope: Where.() -> Unit): SparqlQuery {
        where.apply(scope)
        return this
    }

    fun select(vararg vars: Var): SparqlQuery {
        select = Select(*vars)
        return this
    }

    fun select(scope: Select.Scope.() -> List<TripleItem>): SparqlQuery {
        select = Select(scope(Select.Scope))
        return this
    }

    fun groupBy(vararg vars: Var): SparqlQuery {
        groupBy = GroupBy(*vars)
        return this
    }

    fun orderBy(vararg vars: Var): SparqlQuery {
        orderBy = OrderBy(*vars)
        return this
    }

    fun limit(n: Int): SparqlQuery {
        limit = Limit(n)
        return this
    }

    fun offset(n: Int): SparqlQuery {
        offset = Offset(n)
        return this
    }

    override fun toString() = "$endpoint?force-accept=text%2Fplain&query=${buildQuery()}"

    private fun buildQuery(): String {
        val query = buildString {
            append(prefixes.joinToString(" "))
            append(
                listOfNotNull(
                    select, where,
                    groupBy,
                    orderBy, limit, offset
                ).joinToString(" ")
            )
        }

        return URLEncoder.encode(query, "UTF-8")
    }
}