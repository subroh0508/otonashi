package net.subroh0508.sparkt.core.triples

import net.subroh0508.sparkt.core.aggregates.AggregationFunction
import net.subroh0508.sparkt.core.extensions.camelize
import net.subroh0508.sparkt.core.operators.nodes.Node
import net.subroh0508.sparkt.core.vocabulary.IriVocabulary
import net.subroh0508.sparkt.core.vocabulary.Vocabulary

abstract class TripleFacade internal constructor(
    private val vocabulary: Vocabulary
) {
    fun v(name: String) = vocabulary.v[name.camelize()] ?: Var(name).also { vocabulary.v[name.camelize()] = it }

    val iri: Set<IriVocabulary> get() = vocabulary.iri

    infix fun Node.`as`(v: Var) = Var(v.value, this).also {
        vocabulary.v[v.value.camelize()] = it
    }
    infix fun AggregationFunction.`as`(v: Var) = Var(v.value, this).also {
        vocabulary.v[v.value.camelize()] = it
    }
}
