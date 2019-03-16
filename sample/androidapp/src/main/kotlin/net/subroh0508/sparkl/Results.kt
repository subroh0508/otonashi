package net.subroh0508.sparkl

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val results: List<Bindings>
)