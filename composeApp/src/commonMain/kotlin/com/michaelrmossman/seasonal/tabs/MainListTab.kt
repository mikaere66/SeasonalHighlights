package com.michaelrmossman.seasonal.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.components.MainListHeader
import com.michaelrmossman.seasonal.components.MainListStatus
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.feature_desc
import seasonalhighlights.composeapp.generated.resources.feature_stat

/* Used for both Main List and Faves */
@Composable
fun MainListTab(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    LazyColumn {
        items(
            state.highlights,
            key = { it.shId }
        ) { highlight ->
            Column(modifier = Modifier.padding(all = 16.dp)) {
                MainListHeader(
                    highlight = highlight
                )
                Spacer(Modifier.height(8.dp))
                MainListStatus(
                    highlight = highlight,
                    onEvent = onEvent
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        resource = Res.string.feature_desc,
                        formatArgs = arrayOf(highlight.desc)
                    )
                )
            }
        }
    }
}