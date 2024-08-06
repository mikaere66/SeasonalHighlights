package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState

@Composable
fun BottomSheetContent(
    horizontalPadding: Dp,
    modifier: Modifier,
    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit,
    spacerHeight: Dp,
    state: MainScreenState
) {
    state.currentHighlight?.let { highlight ->
        Column(modifier = modifier) {
            BottomSheetTopBar(
                onClick = onClick,
                onEvent = onEvent
            )

            MainListHeader(
                highlight = highlight
            )

            Spacer(Modifier.height(spacerHeight))

            MainListStatus(
                highlight = highlight,
                onEvent = onEvent
            )

            MainListText(
                highlight = highlight,
                horizontalPadding = horizontalPadding
            )
        }
    }
}