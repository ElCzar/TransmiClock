package com.czar.transmiclock.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.czar.transmiclock.adapter.BusStopHistoryRepository
import com.czar.transmiclock.adapter.TransmilenioApiAdapter
import com.czar.transmiclock.data.BusStop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BusStopViewModel(application: Application) : AndroidViewModel(application) {
    private val api = TransmilenioApiAdapter()
    private val historyRepo = BusStopHistoryRepository(application)

    val searchQuery = mutableStateOf(TextFieldValue(""))
    val searchResults = mutableStateOf<List<BusStop>>(emptyList())
    val selectedBusStop = mutableStateOf<BusStop?>(null)
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    val busStopHistory: StateFlow<List<BusStop>> = historyRepo.historyFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchResults.value = emptyList()
            isLoading.value = true
            error.value = null
            try {
                searchResults.value = api.searchParaderos(query)
            } catch (e: Exception) {
                error.value = e.message
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun selectBusStop(busStop: BusStop) {
        selectedBusStop.value = busStop
        viewModelScope.launch(Dispatchers.IO) {
            historyRepo.addBusStop(busStop)
        }
    }
}