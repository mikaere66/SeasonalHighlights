package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.app_alias
import seasonalhighlights.composeapp.generated.resources.baseline_arrow_back_24
import seasonalhighlights.composeapp.generated.resources.baseline_filter_list_24
import seasonalhighlights.composeapp.generated.resources.baseline_filter_list_off_24
import seasonalhighlights.composeapp.generated.resources.baseline_more_vert_24
import seasonalhighlights.composeapp.generated.resources.baseline_radio_button_checked_24
import seasonalhighlights.composeapp.generated.resources.baseline_radio_button_unchecked_24
import seasonalhighlights.composeapp.generated.resources.desc_menu_overflow
import seasonalhighlights.composeapp.generated.resources.menu_all_seasons
import seasonalhighlights.composeapp.generated.resources.menu_filter_title
import seasonalhighlights.composeapp.generated.resources.setting_navigate_back

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppTopBar(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    var showOverflowMenu: Boolean by remember {
        mutableStateOf(false)
    }
    var showSettingsMenu: Boolean by remember {
        mutableStateOf(false)
    }

    AdaptiveTopAppBar(
        actions = {
            if (
                state.currentScreen == Screen.Main
            ) {
                // Filter by Season (or Reset)
                IconButton(
                    onClick = {
                        when (state.currentSeason) {
                            0 -> showOverflowMenu = !showOverflowMenu
                            else -> onEvent(
                                MainListEvent.SetCurrentSeason(
                                    seasonDbId = 0 // Reset
                                )
                            )
                        }
                    }
                ) {
                    Icon(
                        contentDescription = stringResource(
                            Res.string.menu_filter_title
                        ),
                        imageVector = vectorResource(
                            when (state.currentSeason) {
                                0 -> Res.drawable.baseline_filter_list_24
                                else -> Res.drawable.baseline_filter_list_off_24
                            }
                        )
                    )
                }
                DropdownMenu(
                    expanded = showOverflowMenu,
                    onDismissRequest = { showOverflowMenu = false }
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                        text = stringResource(Res.string.menu_filter_title)
                    )
                    MainMenuItem(
                        onClick = { showOverflowMenu = false },
                        onEvent = onEvent,
                        seasonDbId = 0, // All seasons
                        seasonIcon = vectorResource(
                            when (state.currentSeason) {
                                0 -> Res.drawable.baseline_radio_button_checked_24
                                else -> Res.drawable.baseline_radio_button_unchecked_24
                            }
                        ),
                        seasonName = stringResource(
                            Res.string.menu_all_seasons
                        )
                    )
                    state.seasons.forEachIndexed { index, season ->
                        MainMenuItem(
                            onClick = { showOverflowMenu = false },
                            onEvent = onEvent,
                            seasonDbId = season.seId.toInt(),
                            seasonIcon = vectorResource(
                                /* Remember, season ids start at one */
                                when (state.currentSeason.minus(1)) {
                                    index -> Res.drawable.baseline_radio_button_checked_24
                                    else -> Res.drawable.baseline_radio_button_unchecked_24
                                }
                            ),
                            seasonName = season.code
                        )
                    }
                }
                // App Settings
                IconButton(
                    onClick = { showSettingsMenu = !showSettingsMenu }
                ) {
                    Icon(
                        contentDescription = stringResource(
                            Res.string.desc_menu_overflow
                        ),
                        imageVector = vectorResource(
                            Res.drawable.baseline_more_vert_24
                        )
                    )
                }
                DropdownMenu(
                    expanded = showSettingsMenu,
                    onDismissRequest = { showSettingsMenu = false }
                ) {
                    SettingsMenuItem(
                        onClick = { showSettingsMenu = false },
                        onEvent = onEvent
                    )
                }
            }
        },
        // https://stackoverflow.com/questions/69192042/how-to-use-jetpack-compose-app-bar-backbutton/70409412#70409412
        navigationIcon = {
            if (
                state.currentScreen == Screen.Settings
            ) {
                IconButton(
                    onClick = {
                        onEvent(
                            MainListEvent.SetCurrentScreen(
                                screen = Screen.Main
                            )
                        )
                    }
                ) {
                    Icon(
                        contentDescription = stringResource(
                            resource = Res.string.setting_navigate_back
                        ),
                        imageVector = vectorResource(
                            Res.drawable.baseline_arrow_back_24
                        )
                    )
                }
            }
        },
        title = {
            AdaptiveWidget(
                cupertino = { // TODO
                    Text(
                        text = stringResource(Res.string.app_alias)
                    )
                },
                material = {
                    Text(
                        text = stringResource(Res.string.app_alias),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    )
}