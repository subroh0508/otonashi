package net.subroh0508.sparkl

import kotlinx.serialization.Serializable

@Serializable
data class ValueSet(
    val type: String,
    val value: String
)