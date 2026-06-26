package com.czar.transmiclock.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czar.transmiclock.adapter.TransmilenioApiAdapter
import com.czar.transmiclock.data.Bus
import com.czar.transmiclock.data.BusLocation
import com.czar.transmiclock.data.BusStop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusViewModel : ViewModel() {
    private val api = TransmilenioApiAdapter()

    val busStop = mutableStateOf<BusStop?>(null)
    val buses = mutableStateOf<List<Bus>>(emptyList())
    val selectedBus = mutableStateOf<Bus?>(null)
    val busLocations = mutableStateOf<List<BusLocation>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun fetchBuses() {
        viewModelScope.launch(Dispatchers.IO) {
            buses.value = emptyList()
            isLoading.value = true
            error.value = null
            try {
                buses.value = api.getBusesForParadero(busStop.value?.id.toString())
            } catch (e: Exception) {
                error.value = e.message
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchBusLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            error.value = null
            try {
                busLocations.value = api.getBusesLocation(
                    busStop.value?.codigo.toString(),
                    selectedBus.value?.codigo.toString(),
                    selectedBus.value?.id.toString()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}