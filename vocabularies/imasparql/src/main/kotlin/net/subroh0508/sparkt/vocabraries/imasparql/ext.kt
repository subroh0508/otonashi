package net.subroh0508.sparkt.vocabraries.imasparql

import net.subroh0508.sparkt.core.extensions.get
import net.subroh0508.sparkt.core.triples.TripleFacade
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

val Set<IriVocabulary>.imasC get() = get(ImasparqlClass::class)
val Set<IriVocabulary>.imasP get() = get(ImasparqlProperty::class)

val TripleFacade.imasC get() = iri.imasC
val TripleFacade.imasP get() = iri.imasP