package net.subroh0508.otonashi.core.patterns

import net.subroh0508.otonashi.core.aggregates.GroupBy
import net.subroh0508.otonashi.core.aggregates.Having
import net.subroh0508.otonashi.core.clauses.Clause
import net.subroh0508.otonashi.core.clauses.Select
import net.subroh0508.otonashi.core.operators.nodes.Node
import net.subroh0508.otonashi.core.sequences.Limit
import net.subroh0508.otonashi.core.sequences.Offset
import net.subroh0508.otonashi.core.sequences.OrderBy
import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.triples.Var

class Where internal constructor(vocabulary: Vocabulary) : GraphPattern("WHERE", vocabulary) {
    private var select: Select? = null
    private var groupBy: GroupBy? = null
    private var having: Having? = null
    private var orderBy: OrderBy? = null
    private var limit: Limit? = null
    private var offset: Offset? = null

    fun select(vararg vars: Var): Where {
        select = Select(vocabulary, *vars)
        return this
    }

    fun select(scope: Clause.Scope.() -> List<Any>): Where {
        select = Select(vocabulary, scope(Clause.Scope(vocabulary)))
        return this
    }

    fun groupBy(vararg vars: Var): Where {
        groupBy = GroupBy(vocabulary, *vars)
        return this
    }

    fun groupBy(scope: Clause.Scope.() -> List<Any>): Where {
        groupBy = GroupBy(vocabulary, scope(Clause.Scope(vocabulary)))
        return this
    }

    fun having(scope: Clause.Scope.() -> Node): Where {
        having = Having(vocabulary, scope(Clause.Scope(vocabulary)))
        return this
    }

    fun orderBy(vararg vars: Var): Where {
        orderBy = OrderBy(*vars)
        return this
    }

    fun limit(n: Int): Where {
        limit = Limit(n)
        return this
    }

    fun offset(n: Int): Where {
        offset = Offset(n)
        return this
    }

    override fun toString() =
        if (select == null)
            super.toString()
        else
            buildString {
                append("{ ")
                append("$select ")
                append("$prefix { ")
                append(patterns.joinToString(" "))
                append(" }")
                append(
                    listOfNotNull(
                        groupBy, having,
                        orderBy, limit, offset
                    ).joinToString(" ")
                )
                append(" }")
            }
}