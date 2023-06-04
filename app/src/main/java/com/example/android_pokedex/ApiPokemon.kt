package com.example.android_pokedex

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import com.google.gson.Gson

class ApiPokemon {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getRequest(url: String): Pokemon {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val json = response.body?.string() ?: ""
            return gson.fromJson(json, Pokemon::class.java)
        }
    }
}