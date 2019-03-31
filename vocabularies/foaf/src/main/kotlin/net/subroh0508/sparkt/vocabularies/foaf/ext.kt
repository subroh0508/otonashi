package net.subroh0508.sparkt.vocabularies.foaf

import net.subroh0508.sparkt.triples.TripleFacade
import net.subroh0508.sparkt.triples.extensions.get
import net.subroh0508.sparkt.triples.vocabulary.IriVocabulary

val Set<IriVocabulary>.foaf get() = get(Foaf::class)

val TripleFacade.foaf get() = iri.foaf