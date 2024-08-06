package com.michaelrmossman.seasonal.entities.points.highlights

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureCollectionHighlights(

    @SerialName("type")
    val type: String,

    @SerialName("features")
    val features: List<Feature>
)
