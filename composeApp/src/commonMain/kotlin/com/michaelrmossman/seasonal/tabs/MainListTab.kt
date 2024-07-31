package com.michaelrmossman.seasonal.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.seasonal.components.MainListHeader
import com.michaelrmossman.seasonal.components.MainListStatus
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.feature_desc

@Composable
fun MainListTab(
    horizontalPadding: Dp,
    modifier: Modifier,
    onEvent: (MainListEvent) -> Unit,
    spacerHeight: Dp,
    state: MainScreenState
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(
            items = state.highlights,
            key = { item -> item.shId }
        ) { highlight ->
            Column(modifier = modifier) {
                MainListHeader(
                    highlight = highlight
                )
                Spacer(Modifier.height(spacerHeight))
                MainListStatus(
                    highlight = highlight,
                    onEvent = onEvent
                )
                Text(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding
                    ),
                    text = stringResource(
                        resource = Res.string.feature_desc,
                        formatArgs = arrayOf(highlight.desc)
                    )
                )
            }
        }
    }
}