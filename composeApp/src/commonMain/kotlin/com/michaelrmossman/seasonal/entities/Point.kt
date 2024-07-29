package com.michaelrmossman.seasonal.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Point(
    @SerialName("type")
    val type: String,

    @SerialName("coordinates")
    /* Be aware that these two are reversed (by Google Maps standards)
       i.e. in the .json files, they appear as longitude, latitude */
    val coordinates: List<Double>
)
