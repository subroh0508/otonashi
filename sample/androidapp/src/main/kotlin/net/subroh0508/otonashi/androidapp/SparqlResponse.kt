package net.subroh0508.otonashi.androidapp

import kotlinx.serialization.Serializable

@Serializable
data class SparqlResponse<T: Any>(
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