package com.czar.transmiclock.adapter

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.czar.transmiclock.data.BusStop
import com.czar.transmiclock.data.BusStopHistory
import com.czar.transmiclock.data.BusStopHistorySerializer
import com.czar.transmiclock.data.MAX_HISTORY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.busStopHistoryDataStore: DataStore<BusStopHistory> by dataStore(
    fileName = "bus_stop_history.json",
    serializer = BusStopHistorySerializer,
)

class BusStopHistoryRepository(private val context: Context) {
    val historyFlow: Flow<List<BusStop>> = context.busStopHistoryDataStore.data.map { it.stops }

    suspend fun addBusStop(busStop: BusStop) {
        context.busStopHistoryDataStore.updateData { currentHistory ->
            val newHistory = (listOf(busStop) + currentHistory.stops)
                .distinctBy { it.id }
                .take(MAX_HISTORY)
            currentHistory.copy(stops = newHistory)
        }
    }

    suspend fun clearHistory() {
        context.busStopHistoryDataStore.updateData { BusStopHistory() }
    }
}