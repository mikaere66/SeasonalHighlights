package com.michaelrmossman.seasonal.entities.lines.routes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properties(

    @SerialName("feature")
    val feature: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("desc")
    val description: String? = null,

    @SerialName("oid")
    val id: Long? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("traffic")
    val traffic: String? = null,

    @SerialName("Comment")
    val comment: String? = null,

    @SerialName("RouteArea")
    val area: String? = null,

    @SerialName("RoutePlace")
    val place: String? = null,

    @SerialName("PhotoURL")
    val photo: String? = null
) {
    val photoUrl: String? = when (photo?.isNotBlank()) {
        false -> null
        else -> photo
    }
}
