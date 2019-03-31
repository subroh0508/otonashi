package net.subroh0508.otonashi.vocabularies.foaf

import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.extensions.get
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

val Set<IriVocabulary>.foaf get() = get(Foaf::class)

val TripleFacade.foaf get() = iri.foaf