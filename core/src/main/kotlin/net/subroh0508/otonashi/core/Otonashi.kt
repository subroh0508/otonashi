package net.subroh0508.otonashi.core

import net.subroh0508.otonashi.core.vocabulary.Vocabulary
import net.subroh0508.otonashi.core.vocabulary.common.CommonPrefix
import net.subroh0508.otonashi.core.vocabulary.common.commonVocabularies
import net.subroh0508.otonashi.triples.Prefix
import net.subroh0508.otonashi.triples.vocabulary.IriVocabulary

abstract class Otonashi {
    object Study {
        private lateinit var endpoint: String
        private var prefixes: List<Prefix> = emptyList()
        private var vocabulary: Vocabulary = Vocabulary()

        operator fun invoke(scope: Otonashi.Study.() -> Unit): Kotori {
            val builder = apply(scope)

            return SparqlQuery(builder.endpoint, builder.prefixes, builder.vocabulary)
        }

        fun destination(endpoint: String) {
            this.endpoint = endpoint
        }

        fun reminds(vararg prefixes: Prefix) {
            this.prefixes = listOf(*CommonPrefix.values(), *prefixes)
        }

        fun buildsUp(vararg vocabularies: IriVocabulary) {
            this.vocabulary = Vocabulary(*commonVocabularies, *vocabularies)
        }
    }
}

typealias Kotori = SparqlQuery