package net.subroh0508.otonashi.sample.ktorreact

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import net.subroh0508.otonashi.core.Kotori
import net.subroh0508.otonashi.core.Otonashi
import net.subroh0508.otonashi.core.operators.functions.concat
import net.subroh0508.otonashi.core.operators.functions.contains
import net.subroh0508.otonashi.core.operators.functions.replace
import net.subroh0508.otonashi.core.operators.functions.str
import net.subroh0508.otonashi.core.vocabulary.common.rdfP
import net.subroh0508.otonashi.sample.ktorreact.httpclient.KtorClient
import net.subroh0508.otonashi.sample.ktorreact.model.ImasResult
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
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/name") {
            val idolName = call.request.queryParameters["idol_name"]

            if (idolName == null) {
                call.respond(HttpStatusCode.BadRequest, "specified idol name!")
                return@get
            }

            val results = KtorClient.get(buildQuery(idolName).toString(), ImasResult::class).results()

            call.respondText(Json.indented.stringify(ImasResult.serializer().list, results), contentType = ContentType.Application.Json)
        }
    }
}

fun buildQuery(idolName: String): Kotori = init().where {
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

