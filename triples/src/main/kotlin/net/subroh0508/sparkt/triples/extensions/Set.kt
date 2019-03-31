package net.subroh0508.sparkt.triples.extensions

import net.subroh0508.sparkt.triples.vocabulary.IriVocabulary
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <I: IriVocabulary> Set<IriVocabulary>.get(kClass: KClass<I>) = find { it::class == kClass } as I
