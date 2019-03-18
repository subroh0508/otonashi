package net.subroh0508.sparkt.androidapp

import kotlinx.serialization.Serializable

@Serializable
data class Head(
    val vars: List<String>
)