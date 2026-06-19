package com.czar.transmiclock.data

/**
 * Bus location data
 */
data class BusLocation(
    val vehicleId: Int,
    val etiqueta: String,
    val lastTime: String,
    val minutesAway: Int,
    val distanceMeters: Int
)