package com.michaelrmossman.seasonal.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    @SerialName("type")
    val type: String,

    @SerialName("geometry")
    val geometry: Point,

    @SerialName("properties")
    val properties: Properties
)
