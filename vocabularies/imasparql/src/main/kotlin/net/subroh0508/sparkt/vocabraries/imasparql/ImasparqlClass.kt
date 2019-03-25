package net.subroh0508.sparkt.vocabraries.imasparql

import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

object ImasparqlClass : IriVocabulary(
    "imas",
    "Character", "Idol", "Staff",
    "Unit", "Event", "Facility",
    "CinderellaRankingResult",
    "Live", "SetlistNumber",
    "ScriptText", "Communication", "CallName", "Introduction",
    "Clothes"
) {
    val character by store
    val idol by store
    val staff by store

    val unit by store
    val event by store
    val facility by store

    val cinderellaRankingResult by store

    val live by store
    val setlistNumber by store

    val scriptText by store
    val communication by store
    val callName by store
    val introduction by store

    val clothes by store
}