package net.subroh0508.sparkt.vocabularies.foaf

import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

val Set<IriVocabulary>.foaf get() = get(Foaf::class)

val TripleFacade.foaf get() = iri.foaf