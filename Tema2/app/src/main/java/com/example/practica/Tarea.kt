package com.example.practica

import java.io.Serializable

data class Tarea(
    val nombre: String,
    val descripcion: String,
    val fecha: Long?,
    val prioridad: Int?
): Serializable
