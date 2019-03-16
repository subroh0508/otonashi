package net.subroh0508.sparkl

import kotlinx.serialization.Serializable

@Serializable
data class Head(
    val vars: List<String>
)