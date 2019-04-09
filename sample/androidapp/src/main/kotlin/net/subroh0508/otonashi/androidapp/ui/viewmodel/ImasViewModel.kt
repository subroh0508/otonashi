package net.subroh0508.otonashi.androidapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import net.subroh0508.otonashi.R
import net.subroh0508.otonashi.core.Kotori
import net.subroh0508.otonashi.core.Otonashi
import net.subroh0508.otonashi.core.operators.functions.*
import net.subroh0508.otonashi.core.vocabulary.common.rdfP
import net.subroh0508.otonashi.vocabularies.foaf.FoafPrefix
import net.subroh0508.otonashi.vocabularies.foaf.foafP
import net.subroh0508.otonashi.vocabularies.foaf.foafVocabularies
import net.subroh0508.otonashi.vocabularies.imasparql.ImasparqlPrefix
import net.subroh0508.otonashi.vocabularies.imasparql.imasC
import net.subroh0508.otonashi.vocabularies.imasparql.imasP
import net.subroh0508.otonashi.vocabularies.imasparql.imasparqlVocabularies
import net.subroh0508.otonashi.vocabularies.schema.SchemaPrefix
import net.subroh0508.otonashi.vocabularies.schema.schemaP
import net.subroh0508.otonashi.vocabularies.schema.schemaVocabularies

class ImasViewModel : ViewModel() {
    var contents: List<String> = listOf()
    var idolName: String = ""

    fun buildQuery(context: Context): Kotori = init().where {
        v("s") be {
            rdfP.type to imasC.idol and
            schemaP.name to v("name") and
            imasP.title to v("title") and
            foafP.age to v("age") and
            imasP.bust to v("bust") and
            imasP.waist to v("waist") and
            imasP.hip to v("hip") and
            imasP.bloodType to v("blood_type") and
            imasP.handedness to v("handedness") and
            schemaP.birthDate to v("birth_date") and
            schemaP.birthPlace to v("birth_place")
        }
        filter {
            regex(v("title"), "(${titles(context).joinToString("|")})") and
                contains(v("name"), idolName)
        }
        optional {
            v("s") be { imasP.color to v("color") }
        }
        where {
            v("s") be {
                schemaP.memberOf to v("unit_url")
            }
            v("unit_url") be {
                rdfP.type to imasC.unit and
                schemaP.name to v("unit_name")
            }
        }.select {
            + v("s") + (groupConcat(v("unit_name"), separator = ",") `as` v("unit_names"))
        }.groupBy(v("s"))
    }.select {
        replace(
            str(v("s")),
            """https://sparql.crssnky.xyz/imasrdf/RDFs/detail/""",
            ""
        ) `as` v("id")
        concat("B", str(v("bust")), " W", str(v("waist")), " H", str(v("hip"))) `as` v("three_size")
        concat("#", str(v("color"))) `as` v("color_hex")
        str(v("age")) `as` v("age_str")

        + v("id") + v("name") +
                v("age_str") + v("color_hex") + v("blood_type") + v("handedness") +
                v("birth_date") + v("birth_place") + v("three_size") + v("unit_names", true)
    }

    private fun init() = Otonashi.Study {
        destination("https://sparql.crssnky.xyz/spql/imas/query")
        reminds(SchemaPrefix.SCHEMA, FoafPrefix.FOAF, ImasparqlPrefix.IMAS)
        buildsUp(*schemaVocabularies, *foafVocabularies, *imasparqlVocabularies)
    }

    private fun titles(context: Context): List<String> = contents.mapNotNull {
        when (it) {
            context.getString(R.string.title_765pro_allstars) -> "765AS"
            context.getString(R.string.title_cinderella_girls) -> "CinderellaGirls"
            context.getString(R.string.title_millionstars) -> "MillionStars"
            context.getString(R.string.title_shiny_colors) -> "283Pro"
            context.getString(R.string.title_315_starts) -> "315ProIdols"
            else -> null
        }
    } + (if (contents.contains(context.getString(R.string.title_staff)))
            listOf("765Staff", "876Staff", "961Staff", "315Staff", "283Staff", "CinderellaGirlsStaff")
        else
            listOf()) +
        (if (contents.contains(context.getString(R.string.title_others)))
            listOf("DearlyStars", "961ProIdols")
        else
            listOf())
}