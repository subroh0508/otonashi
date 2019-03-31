package net.subroh0508.otonashi.triples.vocabulary

import net.subroh0508.otonashi.triples.Iri
import kotlin.reflect.KProperty

abstract class IriVocabulary(
    private val prefix: String,
    protected val store: MutableMap<String, Iri> = mutableMapOf()
) {
    constructor(prefix: String, vararg name: String) : this(prefix, name.map { n ->
        n.decapitalize() to Iri("$prefix:$n")
    }.toMap().toMutableMap())

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Iri
        = store[property.name] ?: Iri("$prefix:${property.name}")

    operator fun get(iri: String): Iri {
        val key = iri.decapitalize()

        return store[key] ?: Iri("$prefix:$key").also {
            store[key] = it
        }
    }
}