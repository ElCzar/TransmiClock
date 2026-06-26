package com.czar.transmiclock.data

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Bus stop data
 * Contains information about a bus stop
 */
@Serializable
data class BusStop(
    val id: String = "",
    val codigo: String = "",
    val nombre: String = "",
    val direccion: String = "",
    val colorValue: Long = if (codigo.startsWith("TM"))
        0xFFE57373L
    else
        0xFF64B5F6L
) {
    @Transient
    val color: Color = Color(colorValue)
}

