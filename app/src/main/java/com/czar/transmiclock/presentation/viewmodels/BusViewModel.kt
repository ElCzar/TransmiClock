package com.czar.transmiclock.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czar.transmiclock.adapter.TransmilenioApiAdapter
import com.czar.transmiclock.data.Bus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusViewModel : ViewModel() {
    private val api = TransmilenioApiAdapter()

    val buses = mutableStateOf<List<Bus>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)

    fun fetchBuses(busStopId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            error.value = null
            try {
                buses.value = api.getBusesForParadero(busStopId)
            } catch (e: Exception) {
                error.value = e.message
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}