package com.michaelrmossman.seasonal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrmossman.seasonal.cache.DatabaseImpl
import com.michaelrmossman.seasonal.enums.Setting
import com.michaelrmossman.seasonal.utils.SeasonFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val database: DatabaseImpl
) : ViewModel() {

    private var _currentSeason = MutableStateFlow(0)

    private val seasonFilter = SeasonFilter()

    private fun loadHighlights() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    highlights = emptyList()
                )
            }

            try {
                val highlights = database.getHighlights()
                _state.update { state ->
                    state.copy(
                        highlights = highlights
                    )
                }

            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        highlights = emptyList()
                    )
                }
            }
        }
    }

    private fun loadSeasons() {
        viewModelScope.launch {
            try {
                val seasons = database.getSeasons()
                _state.update { state ->
                    state.copy(
                        seasons = seasons
                    )
                }

            } catch (e: Exception) {
                _state.update { state ->
                    state.copy(
                        seasons = emptyList()
                    )
                }
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                /* RestoreSeason must come before CurrentSeason */
                val saveAndRestore = database.getSetting(
                    Setting.RestoreSeason.name
                ).value_.toBoolean()

                _state.update { state ->
                    state.copy(
                        saveAndRestoreSeason = saveAndRestore
                    )
                }

                _currentSeason.value = when (saveAndRestore) {
                    false -> 0
                    else -> database.getSetting(
                        Setting.CurrentSeason.name
                    ).value_.toInt()
                }

            } catch (e: Exception) {
                _currentSeason.value = 0
            }
        }
    }

    private val _state = MutableStateFlow(MainScreenState())
    val state = combine(
        database.getFavourites(),
        _currentSeason,
        _state
    ) { faves, season, state ->
        val filtered = seasonFilter.filterBySeason(
            state.highlights, season
        )
        state.copy(
            currentSeason = season,
            favourites = faves,
            highlights = filtered
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(
        5000L
    ) , MainScreenState())

    init {
        /* Highlights must come before Seasons */
        loadHighlights()
        loadSeasons()
        loadSettings()
    }

    fun onEvent(event: MainListEvent) {
        when (event) {
            is MainListEvent.SetCurrentSeason -> {
                when (_state.value.saveAndRestoreSeason) {
                    true -> {
                        val result = database.insertSetting(
                            Setting.CurrentSeason.name,
                            event.seasonDbId.toString()
                        )
                        if (result > 0L) {
                            _currentSeason.value = event.seasonDbId
                        }
                    }
                    else -> {
                        _currentSeason.value = event.seasonDbId
                    }
                }
            }
            is MainListEvent.SetCurrentScreen -> {
                _state.update { state ->
                    state.copy(
                        currentScreen = event.screen
                    )
                }
            }
            is MainListEvent.SetShouldSaveSeason -> {
                val result = database.insertSetting(
                    Setting.RestoreSeason.name,
                    event.save.toString()
                )
                if (result > 0L) {
                    _state.update { state ->
                        state.copy(
                            saveAndRestoreSeason = event.save
                        )
                    }
                }
            }
            is MainListEvent.ToggleFave -> {
                when (event.fave) {
                    true -> {
                        database.insertFavourite(event.id)
                    }
                    else -> {
                        database.deleteFavourite(event.id)
                    }
                }
            }
        }
    }
}