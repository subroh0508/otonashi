package net.subroh0508.sparkt.core.triples

data class Var internal constructor(
    internal val value: String,
    private val func: Any?
) : TripleItem {
    constructor(value: String) : this(value, null)

    override fun toString() = if (func == null) "?$value" else "($func as ?$value)"
}