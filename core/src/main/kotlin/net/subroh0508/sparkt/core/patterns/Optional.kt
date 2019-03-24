package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.triples.TriplesStore

class Optional internal constructor(store: TriplesStore) : GraphPattern("OPTIONAL", store)