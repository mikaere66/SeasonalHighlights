package com.michaelrmossman.seasonal.entities.lines.routes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feature(

    @SerialName("type")
    val type: String,

    @Serializable(with = GeometrySerializer::class)
    val geometry: List<Coordinates>,

    @SerialName("properties")
    val properties: Properties
)