package com.czar.transmiclock.data

import kotlinx.serialization.Serializable

/**
 * Bus location data from Transmilenio API
 */
@Serializable
data class BusLocationDto(
    val vehicleid: Int,
    val etiqueta: String,
    val lasttime: String,
    val time: Int,
    val distancia: Int
)

fun BusLocationDto.toBusLocation() = BusLocation(
    vehicleId = vehicleid,
    etiqueta = etiqueta,
    lastTime = lasttime,
    minutesAway = time,
    distanceMeters = distancia
)