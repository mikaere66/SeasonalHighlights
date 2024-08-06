package com.michaelrmossman.seasonal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.seasonal.components.BottomSheetContent
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldDefaults
import io.github.alexzhirkevich.cupertino.PresentationDetent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalCupertinoApi::class)
@Composable
actual fun AppSurface(
    state: MainScreenState,
    spacerHeight: Dp,
    onEvent: (MainListEvent) -> Unit,
    modifier: Modifier,
//    @Suppress("UNUSED_PARAMETER")
//    droidBottomSheetState: BottomSheetScaffoldState,
//    cuperBottomSheetState: CupertinoBottomSheetScaffoldState,
    horizontalPadding: Dp,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberCupertinoBottomSheetScaffoldState(
        rememberCupertinoSheetState(
            presentationStyle = PresentationStyle.Modal(
                detents = setOf(
                    PresentationDetent.Large,
                    PresentationDetent.Fraction(0.6F)
                )
            )
        )
    )
    val shouldShowBottomSheet by state.shouldShowBottomSheet.collectAsState()

    LaunchedEffect(shouldShowBottomSheet) {
        if (shouldShowBottomSheet) {
            scaffoldState.bottomSheetState.expand()
        }
    }

    CupertinoBottomSheetScaffold(
        colors = CupertinoBottomSheetScaffoldDefaults.colors(
            sheetContainerColor = CupertinoTheme.colorScheme
                .secondarySystemBackground
        ),
        scaffoldState = scaffoldState,
        sheetContent = {
           BottomSheetContent(
                horizontalPadding = horizontalPadding,
                modifier = modifier,
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
                onEvent = onEvent,
                spacerHeight = spacerHeight,
                state = state
            )
        },
        sheetSwipeEnabled = false
    ) {
        content()
    }
}