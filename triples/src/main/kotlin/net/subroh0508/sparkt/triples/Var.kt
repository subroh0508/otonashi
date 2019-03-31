package net.subroh0508.sparkt.triples

import net.subroh0508.sparkt.triples.extensions.camelize

data class Var constructor(
    internal val value: String,
    private val renamed: TripleItem?
) : TripleItem {
    constructor(value: String) : this(value, null)
    constructor(v: Var, renamed: TripleItem?) : this(v.value, renamed)

    override fun toString() = if (renamed == null) "?$value" else "($renamed as ?$value)"

    val camelizeName: String get() = value.camelize()
}