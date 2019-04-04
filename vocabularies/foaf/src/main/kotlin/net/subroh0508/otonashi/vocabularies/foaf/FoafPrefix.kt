package net.subroh0508.otonashi.vocabularies.foaf

import net.subroh0508.otonashi.triples.Prefix

/*
 * This file was auto generated by otonashi-vocabularies-generator plugin
 *
 */

enum class FoafPrefix(
    override val prefix: String,
    override val iri: String
) : Prefix {
    FOAF("foaf", "<http://xmlns.com/foaf/0.1/>");

    override fun toString() = "PREFIX $prefix: $iri"
}