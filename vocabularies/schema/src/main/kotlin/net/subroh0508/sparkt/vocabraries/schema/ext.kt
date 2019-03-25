package net.subroh0508.sparkt.vocabraries.schema

import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

val Set<IriVocabulary>.schema get() = get(Schema::class)

val TripleFacade.schema get() = iri.schema