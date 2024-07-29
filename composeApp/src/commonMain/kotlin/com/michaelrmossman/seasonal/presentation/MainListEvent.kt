package com.michaelrmossman.seasonal.presentation

import com.michaelrmossman.seasonal.enums.Screen

sealed interface MainListEvent {

    data class SetCurrentSeason(val seasonDbId : Int): MainListEvent

    data class SetCurrentScreen(val screen : Screen) : MainListEvent

    data class SetShouldSaveSeason(val save: Boolean): MainListEvent

    // TODO: date as String
    data class ToggleFave(val fave: Boolean, val id: Int): MainListEvent
}