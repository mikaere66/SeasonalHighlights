package com.michaelrmossman.seasonal.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.TimeZone

// https://raed-o-ghazal.medium.com/kotlinx-localdatetime-manipulation-for-kmm-eacfede93aba
// https://github.com/Kotlin/kotlinx-datetime

fun LocalDateTime.Companion.now(): LocalDateTime {
    // Returns e.g. 2024-07-28T20:37:00.866
    return Clock.System.now().toLocalDateTime(
        TimeZone.currentSystemDefault()
    )
}

fun LocalDate.Companion.now(): LocalDate {
    // Returns e.g. 2024-07-28
    return LocalDateTime.now().date
}

fun LocalTime.Companion.now(): LocalTime {
    // Returns e.g. 20:37:00.866
    return LocalDateTime.now().time
}