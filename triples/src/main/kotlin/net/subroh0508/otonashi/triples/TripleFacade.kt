package net.subroh0508.otonashi.triples


import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

interface TripleFacade {
    abstract fun v(name: String, onlyName: Boolean = false): Var

    val iri: Set<IriVocabulary>

    infix fun TripleItem.`as`(v: Var): Var
}
