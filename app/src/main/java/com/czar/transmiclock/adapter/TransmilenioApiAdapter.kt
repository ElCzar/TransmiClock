package com.czar.transmiclock.adapter

import com.czar.transmiclock.BuildConfig
import com.czar.transmiclock.data.Bus
import com.czar.transmiclock.data.BusRoutesResponse
import com.czar.transmiclock.data.BusStop
import com.czar.transmiclock.data.BusStopResponse
import com.czar.transmiclock.data.toBus
import com.czar.transmiclock.data.toBusStop
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class TransmilenioApiAdapter {
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    private fun get(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body.string()
        }
    }

    fun searchParaderos(query: String): List<BusStop> {
        val body = get(BuildConfig.URL_PARADEROS + query)
        val parsed = json.decodeFromString<BusStopResponse>(body)
        return parsed.listParadas.map { it.toBusStop() }
    }

    fun getBusesForParadero(busStopId: String): List<Bus> {
        val body = get(BuildConfig.URL_BUSES_PARADERO + busStopId)
        val parsed = json.decodeFromString<BusRoutesResponse>(body)
        return parsed.listaRutas.map { it.toBus() }
    }
}