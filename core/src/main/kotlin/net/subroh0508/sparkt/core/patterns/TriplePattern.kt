package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.triples.TripleItem
import net.subroh0508.sparkt.core.triples.TriplesStore

class TriplePattern internal constructor(
    private val subject: TripleItem,
    store: TriplesStore
) : Pattern, TripleFacade(store) {
    private object SemiColon : TripleItem {
        override fun toString() = ";"
    }

    private object Period : TripleItem {
        override fun toString() = "."
    }

    private object Comma : TripleItem {
        override fun toString() = ","
    }

    private data class Triples(
        val items: List<TripleItem>
    ) : TripleItem {
        constructor(vararg items: TripleItem) : this(items.toList())

        override fun toString() = items.joinToString(" ")
    }

    private val triples: MutableList<TripleItem> = mutableListOf()

    infix fun TripleItem.to(`object`: TripleItem): Triple<TripleItem, TripleItem, TripleItem> {
        triples.addAll(listOf(Triples(subject, this, `object`), Period))

        return Triple(subject, this, `object`)
    }

    infix fun Triple<TripleItem, TripleItem, TripleItem>.and(`object`: TripleItem): Triple<TripleItem, TripleItem, TripleItem> {
        val (_, predication, _) = this

        triples.removeAt(triples.size - 1)
        triples.addAll(listOf(Comma, Triples(`object`), Period))

        return Triple(subject, predication, `object`)
    }

    infix fun Triple<TripleItem, TripleItem, TripleItem>.to(`object`: TripleItem): Triple<TripleItem, TripleItem, TripleItem> {
        val (_, _, predication) = this

        when (triples[triples.size - 3]) {
            is Comma -> (0 until 3).forEach { _ -> triples.removeAt(triples.size - 1) }
            else -> triples.removeAt(triples.size - 1)
        }
        triples.addAll(listOf(SemiColon, Triples(predication, `object`), Period))

        return Triple(subject, predication, `object`)
    }

    override fun toString() = triples.joinToString(" ")
}