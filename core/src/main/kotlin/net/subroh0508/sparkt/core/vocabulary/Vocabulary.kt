package net.subroh0508.sparkt.core.vocabulary

import net.subroh0508.sparkt.triples.Var
import net.subroh0508.sparkt.triples.vocabulary.IriVocabulary

internal class Vocabulary private constructor(
    val v: MutableMap<String, Var> = mutableMapOf(),
    val iri: Set<IriVocabulary>
) {
    constructor(vararg iriVocab: IriVocabulary) : this(iri = setOf(*iriVocab))
}
