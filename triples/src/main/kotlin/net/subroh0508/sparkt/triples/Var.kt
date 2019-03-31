package net.subroh0508.sparkt.triples

import net.subroh0508.sparkt.triples.extensions.camelize

data class Var constructor(
    internal val value: String,
    private val func: Any?
) : TripleItem {
    constructor(value: String) : this(value, null)
    constructor(v: Var, func: Any?) : this(v.value, func)

    override fun toString() = if (func == null) "?$value" else "($func as ?$value)"

    val camelizeName: String get() = value.camelize()
}