package com.czar.transmiclock.data

import androidx.compose.ui.graphics.Color

data class Horario(
    val convencion: String = "",
    val horaInicio: String = "",
    val horaFin: String = ""
)

data class Bus(
    val id: String = "",
    val codigo: String = "",
    val nombre: String = "",
    val color: Color,
    val horarios: List<Horario> = emptyList()
)