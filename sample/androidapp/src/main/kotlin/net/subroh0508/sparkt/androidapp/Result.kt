package net.subroh0508.sparkt.androidapp

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val head: Head,
    val results: Results
)