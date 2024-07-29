package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainScreenState
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.app_subtitle
import seasonalhighlights.composeapp.generated.resources.app_faves
import seasonalhighlights.composeapp.generated.resources.app_items
import seasonalhighlights.composeapp.generated.resources.app_settings

@Composable
fun AppSubtitle(
    state: MainScreenState
) {
    val horizontalPadding = 16.dp
    val subtitle: String
    val verticalPadding = 8.dp

    when (state.currentScreen) {
        Screen.Main -> {
            val current = state.currentSeason
            val substitute = when (current) {
                0 -> stringResource(
                    Res.string.app_items
                )
                else -> state.seasons[
                    current.minus(1)
                ].code
            }
            subtitle = pluralStringResource(
                resource = Res.plurals.app_subtitle,
                quantity = current.plus(1),
                formatArgs = arrayOf(
                    substitute,
                    state.highlights.size
                )
            )
        }
        Screen.Faves -> subtitle = stringResource(
            resource = Res.string.app_faves
        )
        Screen.Settings -> subtitle = stringResource(
            resource = Res.string.app_settings
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val minLines = when (state.currentScreen) {
            Screen.Settings -> 1
            else -> 2
        }
        Text(
            fontWeight = FontWeight.Bold,
            minLines = minLines,
            modifier = Modifier
                .padding(
                start = horizontalPadding,
                end = horizontalPadding,
                bottom = verticalPadding
            ).weight(1F),
            text = subtitle
        )
    }
}