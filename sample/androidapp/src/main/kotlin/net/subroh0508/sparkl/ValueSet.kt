package net.subroh0508.sparkl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValueSet(
    val type: String,
    @SerialName("xml:lang")
    val lang: String?,
    val value: String
)