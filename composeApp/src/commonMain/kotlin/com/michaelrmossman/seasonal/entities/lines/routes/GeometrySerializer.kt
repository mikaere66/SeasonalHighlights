package com.michaelrmossman.seasonal.entities.lines.routes

import io.github.aakira.napier.Napier
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray

object GeometrySerializer: KSerializer<List<Coordinates>> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("type", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): List<Coordinates> {
        val json = ((decoder as JsonDecoder).decodeJsonElement() as JsonObject)
        return parseGeometry(json)
    }

    private fun parseGeometry(json: JsonObject): List<Coordinates> {
        val coordsList = mutableListOf<Coordinates>()

        val type        = json["type"]        ?: return emptyList()
        val coordinates = json["coordinates"] ?: return emptyList()

        try {

            val elementType: String = Json.decodeFromJsonElement(type)
            val coordsArray = coordinates.jsonArray

            if (coordsArray.isNotEmpty()) {

                if (elementType == "MultiLineString") {
                    /*  Both the Bowenvale Valley and Scarborough Bluffs
                        tracks have multi-part lines. We separate each
                        line/section here with coordinates of 0.0,0.0 */

                    coordsArray.forEachIndexed { index, array ->

                        val subArray = array.jsonArray
                        val subList = getSubList(subArray)
                        coordsList.addAll(subList)

                        if (coordsArray.size > 1) {
                            if (index == coordsArray.size.minus(2)) {
                                coordsList.add(
                                    Coordinates(0.0,0.0)
                                )
                            }
                        }
                    }

                } else if (elementType == "LineString") {

                    val subList = getSubList(coordsArray)
                    coordsList.addAll(subList)

                } /* elementTypes of "Point" are ignored */

                // Napier.i("HEY $coordsArray")
                // Napier.i("HEY: $elementType ${ coordsList.size }")

            }

        } catch (exception: Exception) {
            Napier.i("HEY: $exception")
        }

        return coordsList
    }

    private fun getSubList(array: JsonArray): List<Coordinates> {
        val coordsList = mutableListOf<Coordinates>()

        if (array.isNotEmpty()) {
            array.forEach { element ->
                val doubles: List<Double> =
                    Json.decodeFromJsonElement(element)

                // Remember, coords are still reversed
                coordsList.add(
                    Coordinates(
                        longitude = doubles[0],
                        latitude  = doubles[1]
                    )
                )
            }
        }

        return coordsList
    }

    override fun serialize(encoder: Encoder, value: List<Coordinates>) {
        TODO("Not yet implemented")
    }
}