package net.subroh0508.sparkt.core.triples

import net.subroh0508.sparkt.core.vocabulary.Vocabulary
import net.subroh0508.sparkt.triples.TripleFacade
import net.subroh0508.sparkt.triples.TripleItem
import net.subroh0508.sparkt.triples.Var
import net.subroh0508.sparkt.triples.extensions.camelize
import net.subroh0508.sparkt.triples.vocabulary.IriVocabulary

internal class TripleFacadeDelegate(
    private val vocabulary: Vocabulary
) : TripleFacade {
    override fun v(name: String) = vocabulary.v[name.camelize()] ?: Var(name).also { vocabulary.v[name.camelize()] = it }

    override val iri: Set<IriVocabulary> get() = vocabulary.iri

    override fun TripleItem.`as`(v: Var) = Var(v, this).also {
        vocabulary.v[v.camelizeName] = it
    }
}
