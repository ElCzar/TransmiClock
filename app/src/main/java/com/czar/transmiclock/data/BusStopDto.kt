package com.czar.transmiclock.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response from Transmilenio API
 * Contains a list of bus stops
 */
@Serializable
data class BusStopResponse(
    @SerialName("STATUS") val status: Boolean,
    @SerialName("listParadas") val listParadas: List<BusStopDto>
)

/**
 * Bus stop data from Transmilenio API
 * Individual bus stop data
 */
@Serializable
data class BusStopDto(
    val id: String,
    val codigo: String,
    val nombre: String,
    val direccion: String
)

/**
 * Converts a [BusStopDto] to a [BusStop]
 * Automatically obtains the color from the "codigo" field
 */
fun BusStopDto.toBusStop() = BusStop(
    id = id,
    codigo = codigo,
    nombre = nombre,
    direccion = direccion
)