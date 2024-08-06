package com.michaelrmossman.seasonal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.components.BottomSheetContent
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.aakira.napier.Napier
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun AppSurface(
    state: MainScreenState,
    spacerHeight: Dp,
    onEvent: (MainListEvent) -> Unit,
    modifier: Modifier,
//    droidBottomSheetState: BottomSheetScaffoldState,
//    @Suppress("UNUSED_PARAMETER")
//    cuperBottomSheetState: CupertinoBottomSheetScaffoldState,
    horizontalPadding: Dp,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        SheetState(
            density = LocalDensity.current,
            skipHiddenState = false,
            skipPartiallyExpanded = false
        )
    )
    val shouldShowBottomSheet by state.shouldShowBottomSheet.collectAsState()

    LaunchedEffect(scaffoldState.bottomSheetState) {
        if (!scaffoldState.bottomSheetState.hasExpandedState) {
            Napier.i("HEY, cut that out!")
            onEvent(
                MainListEvent.SetCurrentHighlight(
                    hilt = null
                )
            )
        }
    }

    LaunchedEffect(shouldShowBottomSheet) {
        if (shouldShowBottomSheet) {
            scaffoldState.bottomSheetState.expand()

//            snapshotFlow {
//                scaffoldState.bottomSheetState.isVisible
//            }.collect { isVisible ->
//                if (!isVisible) {
//                    Napier.i("HEY, cut that out!")
//                    onEvent(
//                        MainListEvent.SetCurrentHighlight(
//                            hilt = null
//                        )
//                    )
//                }
//            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
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