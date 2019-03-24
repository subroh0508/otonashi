package net.subroh0508.sparkt.core.patterns

import net.subroh0508.sparkt.core.triples.TriplesStore

class Where internal constructor(store: TriplesStore) : GraphPattern("WHERE", store)