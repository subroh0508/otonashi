package net.subroh0508.otonashi.sample.ktorreact

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import net.subroh0508.otonashi.sample.ktorreact.model.CityResult
import net.subroh0508.otonashi.sample.ktorreact.model.ImasResult
import net.subroh0508.otonashi.sample.ktorreact.repository.DBPediaRepository
import net.subroh0508.otonashi.sample.ktorreact.repository.ImasparqlRepository
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        host("localhost:3000")
    }
    install(CallLogging) {
        level = Level.DEBUG
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/imasparql") {
            val idolName = call.request.queryParameters["idol_name"] ?: ""
            val contents = call.request.queryParameters.getAll("contents[]") ?: emptyList()
            val additional = call.request.queryParameters["additional"] ?: ""

            val results = ImasparqlRepository.fetch(idolName, contents, additional)

            call.respondText(Json.indented.stringify(ImasResult.serializer().list, results), contentType = ContentType.Application.Json)
        }

        get("/dbpedia") {
            val prefectureName = call.request.queryParameters["prefecture_name"] ?: ""
            val cityName = call.request.queryParameters["city_name"] ?: ""

            val results = DBPediaRepository.fetch(prefectureName, cityName)

            call.respondText(Json.indented.stringify(CityResult.serializer().list, results))
        }
    }
}


