package com.michaelrmossman.seasonal.cache

import com.michaelrmossman.seasonal.entities.points.highlights.Highlight
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
    fun getFeatureCount(): Long {
        return database.getFeatureCount()
    }

    @Throws(Exception::class)
    fun getHighlights(): Flow<List<Highlight>> {
        /* Database file will map SqlDelight-created
           Features objects to our Highlight objects */
        return database.getFeatures()
    }

    @Throws(Exception::class)
    fun getRouteCount(): Long {
        return database.getRouteCount()
    }

    @Throws(Exception::class)
    fun getSeasons(): List<Seasons> {
        return database.getSeasons()
    }

    @Throws(Exception::class)
    fun getSetting(setting: String): Settings {
        return database.getSetting(setting)
    }

    @Throws(Exception::class)
    fun insertFavourite(featureId: Int): Long {
        return database.insertFavourite(featureId)
    }

    @Throws(Exception::class)
    fun insertSetting(setting: String, value: String): Long {
        return database.insertSetting(setting, value)
    }

    @Throws(Exception::class)
    suspend fun loadHighlights() {
        /* This list maps one or more calendar
           months to a particular (CCC) season */
        val months = getSeasonMonths()

        assetFilenames.forEach { filename ->
            /* Loop through the seven asset files to
               import each feature into the database */
            val fullPath = "files".plus(
                "/"
            ).plus(filename)
            /* Annotation reqd for readBytes.
               Read each file as a ByteArray */
            @OptIn(ExperimentalResourceApi::class)
            val bytes = Res.readBytes(fullPath)
            /* ... then decode the bytes to JSON */
            val jsonString = bytes.decodeToString()
            json.parseHighlightsFile(jsonString).also { collection ->
                database.loadHighlights(collection, months)
            }
        }
    }

    @Throws(Exception::class)
    suspend fun loadRoutes() {
        val fullPath = "files".plus(
            "/"
        ).plus("walking_tracks.geojson")
        /* Annotation reqd for readBytes.
           Read each file as a ByteArray */
        @OptIn(ExperimentalResourceApi::class)
        val bytes = Res.readBytes(fullPath)
        /* ... then decode the bytes to JSON */
        val jsonString = bytes.decodeToString()
        json.parseRoutesFile(jsonString).also { collection ->
            database.loadRoutes(collection)
        }
    }
}