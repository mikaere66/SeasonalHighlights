package com.michaelrmossman.seasonal.entities

data class Highlight(

    val shId: Int,
    val name: String,
    val code: Int,
    val stat: String,
    val desc: String,
    val iUrl: String?,
    val lati: Double,
    val long: Double,
    /* Id of item's calendar month */
    val smId: Int = 0,
    /* Fave timeStamp : when added */
    val time: String?
)
