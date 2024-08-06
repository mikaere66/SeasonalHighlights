package com.michaelrmossman.seasonal.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.michaelrmossman.seasonal.presentation.MainListEvent
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.desc_bottom_sheet_close

@Composable
actual fun BottomSheetTopBar(
    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit
) {
    IconButton(
        onClick = {
            onClick()
            onEvent(
                MainListEvent.SetCurrentHighlight(
                    hilt = null
                )
            )
        }
    ) {
        Icon(
            contentDescription = stringResource(
                Res.string.desc_bottom_sheet_close
            ),
            imageVector = Icons.Outlined.Close
        )
    }
}