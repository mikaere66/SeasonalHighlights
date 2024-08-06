package com.michaelrmossman.seasonal.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.michaelrmossman.seasonal.entities.lines.routes.FeatureCollectionRoutes
import com.michaelrmossman.seasonal.entities.points.highlights.Highlight
import com.michaelrmossman.seasonal.entities.points.highlights.FeatureCollectionHighlights
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
    private val coordinateQueries = database.coordinatesQueries
    private val featureQueries = database.featuresQueries
    private val monthQueries = database.monthsQueries
    private val routeQueries = database.routesQueries
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

    internal fun getRouteCount(): Long {
        return routeQueries.getRouteCount().executeAsOne()
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

    internal fun insertSetting(
        setting: String, value: String
    ) : Long {
        var result = 0L

        settingQueries.transaction {
            settingQueries.insertSetting(setting, value)
            result = settingQueries.selectLastInsertedRowId()
                                   .executeAsOne()
        }

        return result
    }

    internal fun loadHighlights(
        collection: FeatureCollectionHighlights,
        months: HashMap<Long, List<Long>>
    ) {
        var codeId = 0L
        val features = collection.features

        // https://stackoverflow.com/questions/65863956/sqldelight-1-4-how-insert-filled-data-generated-object-without-primarykey-id
        seasonQueries.transaction {
            /* Based on the first item in each asset file,
               insert a new "season" and use its ID below
               when inserting its features. To get the new
               ID, this must be completed in 1 transaction */
            seasonQueries.insertSeason(
                coId = null,
                /* Each and every item in the JSON files has a
                   SeasonalCode name like "Seasonal Highlights
                   <season>", so replace that preceding text
                   with an empty string, e.g. Late Summer */
                code = features[0].properties.seasonalCode.replace(
                    // Note trailing space
                    "Seasonal Highlights ", String()
                )
            )
            codeId = seasonQueries.selectLastInsertedRowId()
                                  .executeAsOne()
        }

        if (codeId > 0L) {
            /* For each calendar month, create an entry
               in the Months table with the month as its
               Id and this codeId in its other column */
            months[codeId]?.forEach { monthId ->
                monthQueries.insertMonth(
                    moId = monthId,
                    coId = codeId
                )
            }

            features.forEach { feature ->
                /* Be aware that these two are reversed (by
                   Google Maps standards) i.e. in the .json
                   files, they appear as longitude|latitude */
                val lati = feature.geometry.coordinates[1]
                val long = feature.geometry.coordinates[0]
                featureQueries.insertFeature(
                    feId = null,
                    name = feature.properties.seasonalName,
                    coId = codeId,
                    stat = feature.properties.status,
                    desc = feature.properties.description,
                    iUrl = feature.properties.photoUrl,
                    lati = lati,
                    long = long,
                    // posi = "$lati,$long",
                    time = String()
                )
            }
        }
    }

    internal fun loadRoutes(collection: FeatureCollectionRoutes) {
        collection.features.forEach { feature ->
            var roId = 0L

//            routeQueries.transaction {
//                routeQueries.insertRoute(
//                    roId = null,
//                    ccId = feature.properties.id,
//                    area = feature.properties.area,
//                    plac = feature.properties.place,
//                    name = feature.properties.name,
//                    stat = feature.properties.status,
//                    comm = feature.properties.comment,
//                    desc = feature.properties.description,
//                    iUrl = feature.properties.photoUrl,
//                    traf = feature.properties.traffic,
//                    time = String()
//                )
//                roId = routeQueries.selectLastInsertedRowId()
//                                   .executeAsOne()
//            }

//            if (roId > 0) {
//                feature.geometry.forEach { coords ->
//                    /* Be aware that these two are reversed (by
//                       Google Maps standards) i.e. in the .json
//                       files, they appear as longitude|latitude */
//                    val lati = coords.latitude
//                    val long = coords.longitude
//                    coordinateQueries.insertCoordinates(
//                        dbId = null,
//                        itId = roId,
//                        posi = "$lati,$long"
//                    )
//                }
//            }
        }
    }
}