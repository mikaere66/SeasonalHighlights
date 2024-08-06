package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.entities.points.highlights.Highlight
import com.michaelrmossman.seasonal.presentation.MainListEvent
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveIconButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.icons.AdaptiveIcons
import io.github.alexzhirkevich.cupertino.adaptive.icons.Favorite
import io.github.alexzhirkevich.cupertino.adaptive.icons.FavoriteBorder
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.desc_fave_add
import seasonalhighlights.composeapp.generated.resources.desc_fave_remove
import seasonalhighlights.composeapp.generated.resources.feature_stat

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun MainListStatus(
    highlight: Highlight,
    onEvent: (MainListEvent) -> Unit
) {
    var checked by rememberSaveable {
        mutableStateOf(
            highlight.time?.isNotEmpty() == true
        )
    }
    val modifier = Modifier.padding(all = 8.dp)
    val statusText = stringResource(
        resource = Res.string.feature_stat,
        formatArgs = arrayOf(highlight.stat)
    )
    val toggleFave = {
        checked = !checked
        onEvent(
            MainListEvent.ToggleFave(
                fave = checked,
                id = highlight.shId
            )
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        AdaptiveWidget(
            cupertino = {
                CupertinoText(
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.weight(1F),
                    text = statusText
                )
            },
            material = {
                Text(
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.weight(1F),
                    text = statusText
                )
            }
        )

        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            val strResource = when (checked) {
                true -> Res.string.desc_fave_remove
                else -> Res.string.desc_fave_add
            }
            val imageVector = when (checked) {
                true -> AdaptiveIcons.Outlined.Favorite
                else -> AdaptiveIcons.Outlined.FavoriteBorder
            }

            AdaptiveIconButton(
                onClick = { toggleFave() }
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = stringResource(
                        resource = strResource
                    )
                )
            }

            // Ensure icon is at the top
            Spacer(Modifier.weight(1F))
        }
    }
}