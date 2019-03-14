package net.subroh0508.core

class WhereScope internal constructor() {
    private var query: String = ""

    fun triple(subject: QueryItem, predication: QueryItem, `object`: QueryItem) {
        if (query.isBlank()) {
            query += listOf(subject, predication, `object`).joinToString(" ")
            return
        }

        query += ". " + listOf(subject, predication, `object`).joinToString(" ")
    }

    operator fun Pair<QueryItem, QueryItem>.unaryPlus() {
        query += "; " + toList().joinToString(" ")
    }

    internal val toQueryStr: String
        get() = "WHERE { $query. }"
}