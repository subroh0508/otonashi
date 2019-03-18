package net.subroh0508.sparkt.androidapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bindings(
    val name: ValueSet,
    val title: ValueSet,
    @SerialName("unit_url")
    val unitUrl: ValueSet
)