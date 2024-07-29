package com.michaelrmossman.seasonal.cache

import com.michaelrmossman.seasonal.entities.Highlight
import com.michaelrmossman.seasonal.utils.Constants.assetFilenames
import com.michaelrmossman.seasonal.utils.DatabaseUtils.getSeasonMonths
import com.michaelrmossman.seasonal.utils.JsonUtils
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import seasonalhighlights.composeapp.generated.resources.Res

class DatabaseImpl(
    databaseDriverFactory: DatabaseDriverFactory,
    private val json: JsonUtils
) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    fun deleteFavourite(featureId: Int): Long {
        return database.deleteFavourite(featureId)
    }

    @Throws(Exception::class)
    fun getFavourites(): Flow<List<Highlight>> {
        return database.getFavourites()
    }

    @Throws(Exception::class)
    suspend fun getHighlights(): List<Highlight> {
        val existingFeatures = database.getFeatures()

        return when (existingFeatures.isNotEmpty()) {
            true -> existingFeatures
            /* If the DB has NOT yet been populated,
               then get busy reading the asset files */
            else -> {
                /* Loop through the seven asset files to
                   import each feature into the database */
                assetFilenames.forEach { filename ->
                    val fullPath = "files".plus(
                        "/"
                    ).plus(filename)
                    /* Annotation reqd for readBytes.
                       Read each file as a ByteArray */
                    @OptIn(ExperimentalResourceApi::class)
                    val bytes = Res.readBytes(fullPath)
                    /* ... then decode the bytes to JSON */
                    val jsonString = bytes.decodeToString()
                    json.parseJsonFile(jsonString).also { collection ->
                        database.populateTables(collection.features)
                    }
                }
                val newFeatures = database.getFeatures()
                Napier.i("SqlDelight features ${ newFeatures.size }")
                newFeatures
            }
        }
    }

    @Throws(Exception::class)
    fun getSeasons(): List<Seasons> {
        return database.getSeasons()
    }

    @Throws(Exception::class)
    fun getSetting(setting: String): Settings {
        return database.getSetting(setting)
    }

    fun insertFavourite(featureId: Int): Long {
        return database.insertFavourite(featureId)
    }

    fun insertSetting(setting: String, value: String): Long {
        return database.insertSetting(setting, value)
    }
}