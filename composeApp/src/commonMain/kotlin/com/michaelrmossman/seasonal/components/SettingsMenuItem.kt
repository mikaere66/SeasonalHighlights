package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainListEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.baseline_settings_24
import seasonalhighlights.composeapp.generated.resources.app_settings

@Composable
fun SettingsMenuItem(
    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier.padding(4.dp),
        onClick = {
            onClick() // Close menu
            onEvent(
                MainListEvent.SetCurrentScreen(
                    screen = Screen.Settings
                )
            )
        },
        text = {
            Text(
                text = stringResource(Res.string.app_settings)
            )
        },
        trailingIcon = {
            // Icon is NOT focusable: no need for contentDescription
            Icon(
                contentDescription = null,
                imageVector = vectorResource(
                    Res.drawable.baseline_settings_24
                )
            )
        }
    )
}