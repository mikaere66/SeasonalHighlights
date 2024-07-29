package com.michaelrmossman.seasonal.utils

import com.michaelrmossman.seasonal.theme.summer
import com.michaelrmossman.seasonal.theme.lateSummer
import com.michaelrmossman.seasonal.theme.autumn
import com.michaelrmossman.seasonal.theme.winter
import com.michaelrmossman.seasonal.theme.lateWinter
import com.michaelrmossman.seasonal.theme.spring
import com.michaelrmossman.seasonal.theme.lateSpring

object Constants {

    /* In order that they appear on CCC website:
       https://www.ccc.govt.nz/parks-and-gardens/christchurch-botanic-gardens/attractions/seasonal-highlights/
    */
    val assetFilenames = listOf(
        "1_summer.json",
        "2_late_summer.json",
        "3_autumn.json",
        "4_winter.json",
        "5_late_winter.json",
        "6_spring.json",
        "7_late_spring.json"
    )

    const val DATABASE_FILENAME = "highlights.db"
    const val DATABASE_VERSION  = 1

    /* Must be in the same order as
       Constants.assetFilenames */
    val seasonColors = listOf(
        summer,
        lateSummer,
        autumn,
        winter,
        lateWinter,
        spring,
        lateSpring
    )

    const val TABLE_COLUMN_SEASON   = "code"

    const val TABLE_NAME_HIGHLIGHTS = "highlights"
    const val TABLE_NAME_MONTHS     = "months"
    const val TABLE_NAME_SEASONS    = "seasons"
}