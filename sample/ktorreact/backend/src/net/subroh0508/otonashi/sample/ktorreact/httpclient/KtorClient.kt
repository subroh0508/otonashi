package net.subroh0508.otonashi.sample.ktorreact.httpclient

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.ContentType
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import net.subroh0508.otonashi.core.serializer.SparqlResponse
import kotlin.reflect.KClass

object KtorClient {
    val client = HttpClient(Apache) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend inline fun <reified T: Any> get(url: String, type: KClass<T>): SparqlResponse<T> {
        val response = client.get<HttpResponse>(url) {
            accept(ContentType("application", "sparql-results+json"))
        }

        @UseExperimental(ImplicitReflectionSerializer::class)
        return Json.nonstrict.parse(
            SparqlResponse.serializer(type.serializer()),
            response.readText(charset = Charsets.UTF_8)
        )
    }
}