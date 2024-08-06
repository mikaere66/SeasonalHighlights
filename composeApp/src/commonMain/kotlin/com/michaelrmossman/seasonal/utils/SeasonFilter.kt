package com.michaelrmossman.seasonal.utils

import com.michaelrmossman.seasonal.entities.points.highlights.Highlight

class SeasonFilter {

    fun filterBySeason(
        highlights: List<Highlight>, season: Int
    ) : List<Highlight> {
        if (highlights.isEmpty()) {
            return highlights
        }

        if (season == 0) { // All seasons
            return highlights
        }

        return highlights.filter { highlight ->
            highlight.coId == season
        }
    }
}
