package net.subroh0508.sparkt.core.vocabulary

import net.subroh0508.sparkt.core.triples.Iri
import kotlin.reflect.KProperty

abstract class IriVocabulary(
    private val prefix: String,
    protected val store: Map<String, Iri> = mapOf()
) {
    constructor(prefix: String, vararg name: String) : this(prefix, name.map { n ->
        n.decapitalize() to Iri("$prefix:$n")
    }.toMap())

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Iri
        = store[property.name] ?: Iri("$prefix:${property.name}")
}