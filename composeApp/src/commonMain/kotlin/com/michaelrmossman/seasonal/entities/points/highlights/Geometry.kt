package com.michaelrmossman.seasonal.entities.points.highlights

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(

    @SerialName("type")
    val type: String,

    /* Be aware that these two are reversed (by Google Maps standards)
       i.e. in the .json files, they appear as longitude, latitude */
    @SerialName("coordinates")
    val coordinates: List<Double>
)
