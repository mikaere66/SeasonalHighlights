package com.michaelrmossman.seasonal.presentation

import com.michaelrmossman.seasonal.entities.points.highlights.Highlight
import com.michaelrmossman.seasonal.enums.Screen

sealed interface MainListEvent {

    data object DeleteAllFavourites: MainListEvent

    data class SetCurrentHighlight(val hilt: Highlight?) : MainListEvent

    data class SetCurrentSeason(val seasonDbId : Int): MainListEvent

    data class SetCurrentScreen(val screen : Screen) : MainListEvent

    data class SetShouldSaveSeason(val save: Boolean): MainListEvent

    data class ToggleFave(val fave: Boolean, val id: Int): MainListEvent
}