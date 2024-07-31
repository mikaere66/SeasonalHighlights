package com.michaelrmossman.seasonal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaelrmossman.seasonal.cache.DatabaseImpl
import com.michaelrmossman.seasonal.enums.Setting
import com.michaelrmossman.seasonal.utils.SeasonFilter
import io.github.aakira.napier.Napier
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

    private val _seasonFilter = SeasonFilter()

    private val _state = MutableStateFlow(MainScreenState())
    val state = combine(
        database.getFavourites(),
        database.getHighlights(),
        _currentSeason,
        _state
    ) { faves, highlights, season, state ->
        val filtered = _seasonFilter.filterBySeason(
            highlights, season
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

    private fun loadHighlights() {
        viewModelScope.launch {
            /* Like everything weird that SqlDelight
               does, it returns COUNT(*) as Long */
            if (database.getFeatureCount() < 1L) {
                /* If the DB has NOT yet been populated,
                   get busy importing the asset files */
                database.populateDb()
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
                /* Restore must come before Current */
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

    fun onEvent(event: MainListEvent) {
        when (event) {
            is MainListEvent.DeleteAllFavourites -> {
                val faves = state.value.favourites
                val iterator = faves.iterator()
                val faveCount = faves.size
                var delCount = 0
                viewModelScope.launch {
                    while (iterator.hasNext()) {
                        val result = database.deleteFavourite(
                            iterator.next().shId
                        )
                        if (result > 0) {
                            delCount = delCount.plus(1)
                        }
                    }
                    if (delCount == faveCount) {
                        _state.update { state ->
                            state.copy(
                                favourites = emptyList()
                            )
                        }
                    }
                }
            }
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
                        currentScreen = MutableStateFlow(
                            event.screen
                        )
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