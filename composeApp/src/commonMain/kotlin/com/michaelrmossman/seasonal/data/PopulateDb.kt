package com.michaelrmossman.seasonal.data

import com.michaelrmossman.seasonal.entities.Feature
import com.michaelrmossman.seasonal.utils.Constants.assetFilenames
import com.michaelrmossman.seasonal.utils.JsonUtils
import org.jetbrains.compose.resources.ExperimentalResourceApi
import seasonalhighlights.composeapp.generated.resources.Res

class PopulateDb {

    suspend fun getFeatures(): List<Feature> {
        val features = mutableListOf<Feature>()
        val json = JsonUtils()

        /* Loop through the seven asset files to
           import each feature into the database */
        assetFilenames.sorted().forEach { filename ->
            val fullPath = "files".plus(
                "/"
            ).plus(filename)
            /* Annotation reqd for readBytes.
               Read each file as a ByteArray */
            @OptIn(ExperimentalResourceApi::class)
            val bytes = Res.readBytes(fullPath)
            /* ... then decode the bytes to JSON */
            val jsonString = bytes.decodeToString()
            val collection = json.parseJsonFile(jsonString)
            // .also { collection ->
            //     database.populateTables(collection.features)
            // }
            features.addAll(collection.features)
        }

        return features
    }
}