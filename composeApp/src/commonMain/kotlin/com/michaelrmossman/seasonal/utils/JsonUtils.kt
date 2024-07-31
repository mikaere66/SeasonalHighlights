package com.michaelrmossman.seasonal.utils

import com.michaelrmossman.seasonal.entities.FeatureCollection
import kotlinx.serialization.json.Json

class JsonUtils {

    fun parseJsonFile(jsonString: String): FeatureCollection {

        return Json.decodeFromString<FeatureCollection>(jsonString)
    }
}