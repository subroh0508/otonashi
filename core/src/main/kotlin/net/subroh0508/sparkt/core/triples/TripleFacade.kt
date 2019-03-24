package net.subroh0508.sparkt.core.triples

import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.operators.nodes.Node

abstract class TripleFacade(protected val store: TriplesStore) {
    val v: TriplesStore.VarStore get() = store.v
    val iri: TriplesStore.IriRefStore get() = store.iri

    operator fun TriplesStore.VarStore.get(key: String): Var {
        val value = store[key] ?: Var(key)

        if (!store.containsKey(key)) {
            store[key] = value
        }

        return value
    }

    operator fun TriplesStore.IriRefStore.get(key: String): IriRef {
        val value = store[key] ?: IriRef(key)

        if (!store.containsKey(key)) {
            store[key] = value
        }

        return value
    }

    operator fun TriplesStore.IriRefStore.set(name: String, iriRef: IriRef) {
        store[name] = iriRef
    }

    operator fun TriplesStore.VarStore.set(name: String, value: Var) {
        store[name] = value
    }

    infix fun Node.`as`(name: String) = Var(name, this).also { v[name] = it }
    infix fun AggregationFunction.`as`(name: String) = Var(name, this).also { v[name] = it }
}