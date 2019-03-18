package net.subroh0508.sparkt.androidapp

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

object KtorClient {
    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }

        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }

    suspend inline fun <reified T: Any> get(url: String, type: KClass<T>): T {
        return GlobalScope.async {
            val response = client.get<String>(url) {
                accept(ContentType("application", "sparql-results+json"))
            }

            Log.d("response", response)
            @UseExperimental(ImplicitReflectionSerializer::class)
            return@async Json.nonstrict.parse(type.serializer(), response)
        }.await()
    }
}