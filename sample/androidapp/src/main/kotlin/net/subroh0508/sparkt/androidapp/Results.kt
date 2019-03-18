package net.subroh0508.sparkt.androidapp

import kotlinx.serialization.Serializable
import net.subroh0508.sparkt.androidapp.Bindings

@Serializable
data class Results(
    val bindings: List<Bindings>
)