package com.czar.transmiclock.data

import androidx.compose.ui.graphics.Color

/**
 * Bus stop data
 * Contains information about a bus stop
 */
data class BusStop(
    val id: String = "",
    val codigo: String = "",
    val nombre: String = "",
    val direccion: String = "",
    val color: Color = if (codigo.startsWith("TM"))
        Color(0xFFE57373)
    else
        Color(0xFF64B5F6)
)