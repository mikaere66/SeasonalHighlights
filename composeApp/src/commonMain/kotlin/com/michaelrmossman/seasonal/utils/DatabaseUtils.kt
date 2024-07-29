package com.michaelrmossman.seasonal.utils

import com.michaelrmossman.seasonal.entities.Highlight

object DatabaseUtils {

    fun getSeasonMonths(): HashMap<Long, Long> {
        /* Give each month a season Id, from Dec.
           to November (Summer to Late Spring) */
        return hashMapOf(
            12L to 1L, // Summer
             1L to 1L,
             2L to 2L, // Late "
             3L to 3L, // Autumn
             4L to 3L,
             5L to 3L,
             6L to 4L, // Winter
             7L to 4L,
             8L to 5L, // Late "
             9L to 6L, // Spring
            10L to 6L,
            11L to 7L  // Late "
        )
    }

    fun mapFeaturesToHighlights(
        feId: Long,
        name: String,
        code: Long,
        stat: String,
        desc: String,
        iUrl: String?,
        lati: Double,
        long: Double,
        moId: Long,
        time: String?
    ) : Highlight {
        return Highlight(
            shId = feId.toInt(),
            name = name,
            code = code.toInt(),
            stat = stat,
            desc = desc,
            iUrl = iUrl,
            lati = lati,
            long = long,
            smId = moId.toInt(),
            time = time
        )
    }
}