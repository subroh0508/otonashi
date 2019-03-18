package net.subroh0508.sparkt.core.scopes

import net.subroh0508.sparkt.core.QueryItem

class WhereScope internal constructor() {
    private object SemiColon : QueryItem {
        override fun toString() = ";"
    }

    private val triples: MutableList<List<QueryItem>> = mutableListOf()

    infix fun QueryItem.be(predication: QueryItem) = Pair(this, predication)

    infix fun Pair<QueryItem, QueryItem>.to(`object`: QueryItem): Triple<QueryItem, QueryItem, QueryItem> {
        val (subject, predication) = this
        triples.add(listOf(subject, predication, `object`))

        return Triple(subject, predication, `object`)
    }

    infix fun Triple<QueryItem, QueryItem, QueryItem>.and(predication: QueryItem): QueryItem {
        triples[triples.size - 1] = triples.last() + listOf(SemiColon, predication)

        return predication
    }

    infix fun QueryItem.to(`object`: QueryItem): Triple<QueryItem, QueryItem, QueryItem> {
        val lastSubject = triples.last().last()
        triples[triples.size - 1] = triples.last() + listOf(`object`)

        return Triple(lastSubject, this, `object`)
    }

    override fun toString() = buildString {
        append("WHERE { ")
        append(triples.joinToString(" ") { triple -> triple.joinToString(" ") })

        append(". }")
    }
}