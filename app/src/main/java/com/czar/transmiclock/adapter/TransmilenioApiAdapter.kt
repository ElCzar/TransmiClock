package com.czar.transmiclock.adapter

import com.czar.transmiclock.BuildConfig
import com.czar.transmiclock.data.Bus
import com.czar.transmiclock.data.BusLocation
import com.czar.transmiclock.data.BusLocationDto
import com.czar.transmiclock.data.BusRoutesResponse
import com.czar.transmiclock.data.BusStop
import com.czar.transmiclock.data.BusStopResponse
import com.czar.transmiclock.data.toBus
import com.czar.transmiclock.data.toBusLocation
import com.czar.transmiclock.data.toBusStop
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class TransmilenioApiAdapter {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
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

    fun getBusesLocation(busStopCodigo: String, busCodigo: String, busId: String, busName: String = "", busDistancia: String = "100"): List<BusLocation> {
        val postBody = json.encodeToString(mapOf(
            "estacion" to busStopCodigo,
            "ruta" to busCodigo,
            "idRuta" to busId,
            "Nombre" to busName,
            "Distancia" to busDistancia
        ))

        val request = Request.Builder()
            .url(BuildConfig.URL_LOCALIZACION_BUSES)
            .addHeader("Appid", BuildConfig.APPID)
            .post(postBody.toRequestBody(MEDIA_TYPE_JSON))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val body = response.body.string()
            if (body.isBlank()) return emptyList()
            val parsed = json.decodeFromString<List<BusLocationDto>>(body)
            return parsed.map { it.toBusLocation() }
        }
    }

    companion object {
        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    }
}