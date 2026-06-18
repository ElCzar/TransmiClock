package com.czar.transmiclock.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czar.transmiclock.adapter.TransmilenioApiAdapter
import com.czar.transmiclock.data.BusStop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusStopViewModel : ViewModel() {
    private val api = TransmilenioApiAdapter()

    val searchResults = mutableStateOf<List<BusStop>>(emptyList())
    val selectedBusStop = mutableStateOf<BusStop?>(null)
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
    }
}