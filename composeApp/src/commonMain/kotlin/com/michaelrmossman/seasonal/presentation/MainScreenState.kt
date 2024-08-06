package com.michaelrmossman.seasonal.presentation

import com.michaelrmossman.seasonal.cache.Seasons
import com.michaelrmossman.seasonal.entities.points.highlights.Highlight
import com.michaelrmossman.seasonal.enums.Screen
import kotlinx.coroutines.flow.MutableStateFlow

data class MainScreenState(

    val currentHighlight: Highlight? = null,

    val currentSeason: Int = 0, // All seasons

    val currentScreen: MutableStateFlow<Screen> =
        MutableStateFlow(Screen.Main),

    val favourites: List<Highlight> = emptyList(),
    val highlights: List<Highlight> = emptyList(),

    val saveAndRestoreSeason: Boolean = false,
    val seasons: List<Seasons> = emptyList(),

    val shouldShowBottomSheet: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
)