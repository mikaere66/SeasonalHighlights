package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.utils.Constants.seasonColors

@Composable
fun MainMenuItem(
    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit,
    seasonDbId: Int,
    seasonIcon: ImageVector,
    seasonName: String
) {
    DropdownMenuItem(
        modifier = Modifier.padding(4.dp),
        onClick = {
            onClick() // Close menu
            onEvent(
                MainListEvent.SetCurrentSeason(
                    seasonDbId = seasonDbId
                )
            )
        },
        text = {
            Text(
                text = seasonName
            )
        },
        trailingIcon = {
            // Icon is NOT focusable: no need for contentDescription
            Icon(
                contentDescription = null,
                imageVector = seasonIcon,
                tint = when (seasonDbId) {
                    0 -> when (isSystemInDarkTheme()) {
                        true -> Color.White
                        else -> Color.Black
                    }
                    /* Remember, season ids start at one */
                    else -> seasonColors[seasonDbId.minus(1)]
                }
            )
        }
    )
}