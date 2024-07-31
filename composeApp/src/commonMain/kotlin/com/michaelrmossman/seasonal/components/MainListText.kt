package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.michaelrmossman.seasonal.entities.Highlight
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.feature_desc

@Composable
fun MainListText(
    highlight: Highlight,
    horizontalPadding: Dp
) {
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