package net.subroh0508.otonashi.core.serializer

import kotlinx.serialization.Serializable

@Serializable
data class SparqlResponse<T>(
    val head: Head,
    @Serializable(with = ResultsSerializer::class)
    val results: Results<T>
) {

    @Serializable
    data class Head(
        val vars: List<String>
    )

    data class Results<T>(
        val bindings: List<T>
    )
}