package net.subroh0508.otonashi.vocabulary.generator.internal

import okhttp3.OkHttpClient
import okhttp3.Request

internal object HttpClient {
    private val client = OkHttpClient()

    fun fetch(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).execute().body()?.string() ?: throw IllegalStateException("Empty body")
    }
}