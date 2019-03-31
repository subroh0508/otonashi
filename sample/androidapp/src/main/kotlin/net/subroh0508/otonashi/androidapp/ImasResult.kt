package net.subroh0508.otonashi.androidapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImasResult(
    val id: ValueSet,
    val name: ValueSet,
    @SerialName("unit_names")
    val unitNames: ValueSet
)