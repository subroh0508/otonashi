package net.subroh0508.otonashi.vocabraries.schema

import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.extensions.get
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

val Set<IriVocabulary>.schema get() = get(Schema::class)

val TripleFacade.schema get() = iri.schema