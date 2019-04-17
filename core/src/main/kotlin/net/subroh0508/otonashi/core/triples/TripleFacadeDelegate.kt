package net.subroh0508.otonashi.core.triples

import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.triples.Iri
import net.subroh0508.otonashi.triples.TripleFacade
import net.subroh0508.otonashi.triples.TripleItem
import net.subroh0508.otonashi.triples.Var
import net.subroh0508.otonashi.triples.extensions.camelize
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

internal class TripleFacadeDelegate(
    private val vocabulary: Vocabulary
) : TripleFacade {
    override fun v(
        name: String,
        onlyName: Boolean
    ) = if (onlyName)
            Var(name)
        else
            vocabulary.v[name.camelize()] ?: Var(name).also { vocabulary.v[name.camelize()] = it }

    override fun iri(name: String) = Iri(name)

    override val iri: Set<IriVocabulary> get() = vocabulary.iri

    override fun TripleItem.`as`(v: Var) = Var(v, this).also {
        vocabulary.v[v.camelizeName] = it
    }
}
