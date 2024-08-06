package com.michaelrmossman.seasonal.entities.lines.routes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureCollectionRoutes(

    @SerialName("type")
    val type: String,

    @SerialName("features")
    val features: List<Feature>
)