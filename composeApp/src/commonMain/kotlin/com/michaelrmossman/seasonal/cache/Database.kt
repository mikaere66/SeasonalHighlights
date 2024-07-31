package com.michaelrmossman.seasonal.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.michaelrmossman.seasonal.entities.Highlight
import com.michaelrmossman.seasonal.entities.Feature
import com.michaelrmossman.seasonal.utils.DatabaseUtils.mapFeaturesToHighlights
import com.michaelrmossman.seasonal.utils.now
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

internal class Database(
    databaseDriverFactory: DatabaseDriverFactory
) {

    private val database = SeasonalDatabase(
        databaseDriverFactory.createDriver()
    )
    private val featureQueries = database.featuresQueries
    private val seasonQueries = database.seasonsQueries
    private val settingQueries = database.settingsQueries

    @Throws(Exception::class)
    fun deleteFavourite(featureId: Int): Long {
        var result = 0L

        featureQueries.transaction {
            /* timeStamp must come before featureId
               to match SqlDelight signature */
            featureQueries.toggleFavourite(
                String(), featureId.toLong()
            )
            result = featureQueries.selectChanges().executeAsOne()
        }

        return result
    }

    internal fun getFavourites(): Flow<List<Highlight>> {
        return featureQueries.getFavourites(
            ::mapFeaturesToHighlights
        ).asFlow().mapToList(
            Dispatchers.IO
        )
    }

    internal fun getFeatureCount(): Long {
        return featureQueries.getFeatureCount().executeAsOne()
    }

    internal fun getFeatures(): Flow<List<Highlight>> {
        /* Here, we need to map SqlDelight-created
           object Features to our Highlight object */
        return featureQueries.getFeatures(
            ::mapFeaturesToHighlights
        ).asFlow().mapToList(
            Dispatchers.IO
        )
    }

    internal fun getSeasons(): List<Seasons> {
        return seasonQueries.getSeasons().executeAsList()
    }

    internal fun getSetting(setting: String): Settings {
        return settingQueries.getSetting(setting).executeAsOne()
    }

    internal fun insertFavourite(featureId: Int): Long {
        var result = 0L
        val timeStamp = LocalDateTime.now().toString()

        featureQueries.transaction {
            /* timeStamp must come before featureId
               to match SqlDelight signature */
            featureQueries.toggleFavourite(
                timeStamp, featureId.toLong()
            )
            result = featureQueries
                     .selectLastInsertedRowId().executeAsOne()
        }

        return result
    }

    internal fun insertSetting(setting: String, value: String): Long {
        var result = 0L

        settingQueries.transaction {
            settingQueries.insertSetting(setting, value)
            result = settingQueries.selectLastInsertedRowId().executeAsOne()
        }

        return result
    }

    internal fun populateTables(features: List<Feature>) {
        var seasonId = 0L

        // https://stackoverflow.com/questions/65863956/sqldelight-1-4-how-insert-filled-data-generated-object-without-primarykey-id
        seasonQueries.transaction {
            /* Based on the first item in each asset file,
               insert a new "season" and use its ID below
               when inserting its features. To get the new
               ID, this must be completed in 1 transaction */
            seasonQueries.insertSeason(
                seId = null,
                /* Each and every item in the JSON files has a
                   SeasonalCode name like "Seasonal Highlights
                   <season>", so replace that preceding text
                   with an empty string, e.g. Late Summer */
                code = features[0].properties.seasonalCode.replace(
                    // Note trailing space
                    "Seasonal Highlights ", String()
                )
            )
            seasonId = seasonQueries.selectLastInsertedRowId().executeAsOne()
        }

        Napier.i("SqlDelight seasonId 00$seasonId")

        if (seasonId > 0L) {
            features.forEach { feature ->
                featureQueries.insertFeature(
                    feId = null,
                    name = feature.properties.seasonalName,
                    code = seasonId,
                    stat = feature.properties.status,
                    desc = feature.properties.description,
                    iUrl = feature.properties.photoUrl,
                    /* Be aware that these two are reversed (by
                       Google Maps standards) i.e. in the .json
                       files, they appear as longitude|latitude */
                    lati = feature.geometry.coordinates[1],
                    long = feature.geometry.coordinates[0],
                    moId = 0L,
                    time = String()
                )
            }
        }
    }
}