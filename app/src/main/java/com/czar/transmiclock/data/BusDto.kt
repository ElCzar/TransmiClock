package com.czar.transmiclock.data

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import androidx.core.graphics.toColorInt

/**
 * Bus Routes Response
 */
@Serializable
data class BusRoutesResponse(
    @SerialName("lista_rutas") val listaRutas: List<BusDto>
)

/**
 * Bus Data Transfer Object
 * Contains bus information from the API
 */
@Serializable
data class BusDto(
    val id: String,
    val codigo: String,
    val nombre: String,
    val color: String,
    val horarios: HorariosDto
)

/**
 * Bus Schedule Response
 */
@Serializable
data class HorariosDto(
    val data: List<HorarioDto>
)

/**
 * Bus Schedule Data Transfer Object
 * Contains bus schedule information from the API
 */
@Serializable
data class HorarioDto(
    val convencion: String,
    @SerialName("hora_inicio") val horaInicio: String,
    @SerialName("hora_fin") val horaFin: String
)

/**
 * Converts BusDto to Bus
 */
fun BusDto.toBus() = Bus(
    id = id,
    codigo = codigo,
    nombre = nombre,
    color = Color(color.toColorInt()),
    horarios = horarios.data.map {
        Horario(
            convencion = it.convencion,
            horaInicio = it.horaInicio,
            horaFin = it.horaFin
        )
    }
)