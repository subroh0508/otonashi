package net.subroh0508.sparkt.core.vocabulary.common

import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

val Set<IriVocabulary>.rdf get() = this.get(Rdf::class)

val TripleFacade.rdf get() = iri.rdf