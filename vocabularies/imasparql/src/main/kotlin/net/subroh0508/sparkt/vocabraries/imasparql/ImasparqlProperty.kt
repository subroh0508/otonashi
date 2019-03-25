package net.subroh0508.sparkt.vocabraries.imasparql

import net.subroh0508.sparkt.core.vocabulary.IriVocabulary

object ImasparqlProperty : IriVocabulary(
    "imas",
    "familyNameKana", "givenNameKana", "nameKana", "alternateNameKana",
    "Attribute", "Type", "Category", "Division",
    "BloodType", "Bust", "Waist", "ShoeSize", "Hip", "Handedness",
    "Hobby", "Talent", "Favorite", "Color",
    "cv",
    "Constellation",
    "VoteNumber", "TuneNumber", "Performance", "NumPerformance", "NumPerformanceEncore",
    "Script", "ScriptNumber",
    "Source", "SpeakerLabel",
    "Destination", "Called",
    "Title", "Whose"
) {
    val familyNameKana by store
    val givenNameKana by store
    val nameKana by store
    val alternateNameKana by store

    val attribute by store
    val type by store
    val category by store
    val division by store

    val bloodType by store
    val bust by store
    val waist by store
    val shoeSize by store
    val hip by store
    val handedness by store

    val hobby by store
    val talent by store
    val favorite by store
    val color by store

    val cv by store

    val constellation by store

    val voteNumber by store
    val tuneNumber by store
    val performance by store
    val numPerformance by store
    val numPerformanceEncore by store

    val script by store
    val scriptNumber by store

    val source by store
    val speakerLabel by store

    val destination by store
    val called by store

    val title by store
    val whose by store
}