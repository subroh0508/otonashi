package net.subroh0508.otonashi.core.vocabulary.common

import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.extensions.get
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

val Set<IriVocabulary>.rdf get() = this.get(Rdf::class)

val TripleFacade.rdf get() = iri.rdf