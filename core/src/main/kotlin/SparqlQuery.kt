package net.subroh0508.core

class SparqlQuery(private val endpoint: String) {
    private val elements: MutableList<String> = mutableListOf()

    fun where(scope: WhereScope.() -> Unit): SparqlQuery {
        elements.add(WhereScope().apply(scope).toQueryStr)
        return this
    }
}