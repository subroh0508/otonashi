package net.subroh0508.sparkt.core.scopes

class OptionalScope internal constructor() : WhereScope() {
    override fun toString() = buildString {
        append("OPTIONAL { ")
        append(joinedTriples)
        append(". }")
    }
}