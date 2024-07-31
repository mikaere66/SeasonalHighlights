package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.entities.Highlight
import com.michaelrmossman.seasonal.utils.Constants.seasonColors
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun MainListHeader(
    highlight: Highlight
) {
    Row(
        modifier = Modifier
            /* Find colour for this season by
               subtracting 1 from seasonId */
            .background(
                seasonColors[highlight.code.minus(1)]
            )
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        AdaptiveWidget(
            cupertino = {
                CupertinoText(
                    style = CupertinoTheme
                        .typography.headline,
                    text = highlight.name
                )
            },
            material = {
                Text(
                    style = MaterialTheme
                        .typography.headlineSmall,
                    text = highlight.name
                )
            }
        )
    }
}