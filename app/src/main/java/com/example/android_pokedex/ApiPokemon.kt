package com.example.android_pokedex

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiPokemon {
    private val client = OkHttpClient()

    fun getRequest(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            return response.body?.string() ?: ""
        }
    }
}