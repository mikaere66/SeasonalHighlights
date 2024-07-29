package com.michaelrmossman.seasonal.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.michaelrmossman.seasonal.components.AppBottomBar
import com.michaelrmossman.seasonal.components.AppSubtitle
import com.michaelrmossman.seasonal.components.AppTopBar
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainViewModel
import com.michaelrmossman.seasonal.tabs.MainListTab
import com.michaelrmossman.seasonal.tabs.SettingsTab
import com.michaelrmossman.seasonal.theme.AppTheme
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.koin.compose.currentKoinScope

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun MainScreen() {
    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsState()

    AppTheme {
        AdaptiveScaffold(
            topBar = {
                AppTopBar(
                    onEvent = viewModel::onEvent,
                    state = state
                )
            },
            bottomBar = {
                AppBottomBar(
                    onEvent = viewModel::onEvent,
                    state = state
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                AppSubtitle(state = state)

                // Napier.i("Hey, ${ state.currentScreen.name }")

                when (state.currentScreen) {
                    Screen.Settings -> {
                        SettingsTab(
                            onEvent = viewModel::onEvent,
                            state = state
                        )
                    }
                    /* Full/filtered list OR favourites */
                    else -> {
                        MainListTab(
                            onEvent = viewModel::onEvent,
                            state = state
                        )
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}