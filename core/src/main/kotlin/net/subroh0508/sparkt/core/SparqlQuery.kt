package net.subroh0508.sparkt.core

import net.subroh0508.sparkt.core.aggregates.GroupBy
import net.subroh0508.sparkt.core.aggregates.Having
import net.subroh0508.sparkt.core.clauses.Clause
import net.subroh0508.sparkt.core.clauses.Select
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.patterns.Where
import net.subroh0508.sparkt.core.sequences.Limit
import net.subroh0508.sparkt.core.sequences.Offset
import net.subroh0508.sparkt.core.sequences.OrderBy
import net.subroh0508.sparkt.core.triples.Var
import java.net.URLEncoder

class SparqlQuery(
    private val endpoint: String,
    private val prefixes: List<Prefix>
) {
    private var select: Select = Select(Var("*"))
    private val where: Where by lazy(::Where)
    private var groupBy: GroupBy? = null
    private var having: Having? = null
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

    fun select(scope: Clause.Scope.() -> List<Any>): SparqlQuery {
        select = Select(scope(Clause.Scope))
        return this
    }

    fun groupBy(vararg vars: Var): SparqlQuery {
        groupBy = GroupBy(*vars)
        return this
    }

    fun groupBy(scope: Clause.Scope.() -> List<Any>): SparqlQuery {
        groupBy = GroupBy(scope(Clause.Scope))
        return this
    }

    fun having(scope: Clause.Scope.() -> Node): SparqlQuery {
        having = Having(scope(Clause.Scope))
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
                    groupBy, having,
                    orderBy, limit, offset
                ).joinToString(" ")
            )
        }

        return URLEncoder.encode(query, "UTF-8")
    }
}