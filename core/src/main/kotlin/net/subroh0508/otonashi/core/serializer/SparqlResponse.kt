package net.subroh0508.otonashi.core.serializer

import kotlinx.serialization.Serializable
import net.subroh0508.otonashi.core.serializer.rdfelement.RDFElement

@Serializable
data class SparqlResponse<T> internal constructor(
    internal val head: Head,
    @Serializable(with = ResultsSerializer::class)
    internal val results: Results<T>
) {
    @Serializable
    internal data class Head(
        val vars: List<String>
    )

    internal data class Results<T>(
        val bindings: List<Binding<T>>
    )

    internal data class Binding<T>(
        val rawValue: Map<String, RDFElement?>,
        val value: T
    )

    fun head(): List<String> = head.vars
    fun results(): List<T> = results.bindings.map(Binding<T>::value)
    fun rawResults(): List<Map<String, RDFElement?>> = results.bindings.map(Binding<T>::rawValue)
}