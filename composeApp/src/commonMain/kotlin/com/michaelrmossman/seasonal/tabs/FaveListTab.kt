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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.components.MainListHeader
import com.michaelrmossman.seasonal.components.MainListStatus
import com.michaelrmossman.seasonal.components.MainListText
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.fave_added

/* Apart from state.favourites vs state.highlights,
   only diffs between FaveListTab & MainListTab are
   LazyColumn key and the "Fave added" timeStamp */
@Composable
fun FaveListTab(
//    cuperOnClick: () -> Unit,
//    droidOnClick: () -> Unit,
    horizontalPadding: Dp,
    modifier: Modifier,
    onEvent: (MainListEvent) -> Unit,
    spacerHeight: Dp,
    state: MainScreenState
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(
            items = state.favourites,
            key = { item -> item.shId.unaryMinus() }
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
                MainListText(
                    highlight = highlight,
                    horizontalPadding = horizontalPadding
                )
                if (
                    highlight.time?.isNotBlank() == true
                ) {
                    val date = highlight.time.substring(
                        0,
                        highlight.time.indexOf("T")
                    )
                    val time = highlight.time.substring(
                        date.length.plus(1),
                        highlight.time.lastIndexOf(":")
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .alpha(0.66F)
                            .padding(
                                horizontal = horizontalPadding
                            ),
                        text = stringResource(
                            resource = Res.string.fave_added,
                            formatArgs = arrayOf(date, time)
                        )
                    )
                }
            }
        }
    }
}