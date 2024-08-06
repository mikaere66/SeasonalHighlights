package com.michaelrmossman.seasonal

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun AppSurface(
    state: MainScreenState,
    spacerHeight: Dp,
    onEvent: (MainListEvent) -> Unit,
    modifier: Modifier = Modifier,
//    droidBottomSheetState: BottomSheetScaffoldState,
//    cuperBottomSheetState: CupertinoBottomSheetScaffoldState,
    horizontalPadding: Dp,
    content: @Composable () -> Unit
)