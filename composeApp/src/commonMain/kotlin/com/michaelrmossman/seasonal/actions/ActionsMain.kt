package com.michaelrmossman.seasonal.actions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.components.MainMenuItem
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.baseline_filter_list_24
import seasonalhighlights.composeapp.generated.resources.baseline_filter_list_off_24
import seasonalhighlights.composeapp.generated.resources.baseline_radio_button_checked_24
import seasonalhighlights.composeapp.generated.resources.baseline_radio_button_unchecked_24
import seasonalhighlights.composeapp.generated.resources.menu_all_seasons
import seasonalhighlights.composeapp.generated.resources.menu_filter_title

@Composable
fun ActionsMain(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    var showOverflowMenu: Boolean by remember {
        mutableStateOf(false)
    }

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
}