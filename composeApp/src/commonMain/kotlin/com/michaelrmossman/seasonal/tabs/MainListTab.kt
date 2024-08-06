package com.michaelrmossman.seasonal.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.seasonal.components.MainListHeader
import com.michaelrmossman.seasonal.components.MainListStatus
import com.michaelrmossman.seasonal.components.MainListText
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.feature_desc

@Composable
fun MainListTab(
    horizontalPadding: Dp,
    modifier: Modifier,
//    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit,
    spacerHeight: Dp,
    state: MainScreenState
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(
            items = state.highlights,
            key = { item -> item.shId }
        ) { highlight ->
            Column(modifier = modifier.clickable {
//                coroutineScope.launch {
                    onEvent(
                        MainListEvent.SetCurrentHighlight(
                            highlight
                        )
                    )
//                }
            }) {
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
}