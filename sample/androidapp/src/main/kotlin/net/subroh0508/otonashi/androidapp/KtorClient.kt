package net.subroh0508.otonashi.androidapp

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
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
    val client = HttpClient(Android)

    suspend inline fun <reified T: Any> get(url: String, type: KClass<T>): SparqlResponse<T> {
        val response = client.get<HttpResponse>(url) {
            accept(ContentType("application", "sparql-results+json"))
        }

        @UseExperimental(ImplicitReflectionSerializer::class)
        return Json.unquoted.parse(
            SparqlResponse.serializer(type.serializer()),
            response.readText(charset = Charsets.UTF_8)
        )
    }
}