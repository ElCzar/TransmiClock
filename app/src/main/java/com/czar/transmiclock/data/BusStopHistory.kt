package com.czar.transmiclock.data

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

const val MAX_HISTORY = 5

@Serializable
data class BusStopHistory(
    val stops: List<BusStop> = emptyList()
)

object BusStopHistorySerializer : Serializer<BusStopHistory> {
    override val defaultValue: BusStopHistory = BusStopHistory()

    override suspend fun readFrom(input: InputStream): BusStopHistory =
        try {
            Json.decodeFromString<BusStopHistory>(
                input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw Exception("Unable to read BusStopHistory", e)
        }

    override suspend fun writeTo(t: BusStopHistory, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(Json.encodeToString(BusStopHistory.serializer(), t).encodeToByteArray())
        }
    }
}
