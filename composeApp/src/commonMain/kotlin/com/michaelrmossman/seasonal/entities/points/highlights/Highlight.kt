package com.michaelrmossman.seasonal.entities.points.highlights

data class Highlight(

    val shId: Int,
    val name: String,
    val coId: Int,
    val stat: String,
    val desc: String,
    val iUrl: String?,
    val lati: Double,
    val long: Double,
    // val posi: String?,
    /* Fave timeStamp, when added */
    val time: String?
)
