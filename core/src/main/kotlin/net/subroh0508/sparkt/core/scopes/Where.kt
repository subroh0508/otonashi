package net.subroh0508.sparkt.core.scopes

import net.subroh0508.sparkt.core.patterns.GraphPattern

class Where internal constructor() : GraphPattern("WHERE") {
    fun optional(optional: Optional.() -> Unit): Where {
        patterns.add(Optional().apply(optional))
        return this
    }
}