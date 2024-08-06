package com.michaelrmossman.seasonal.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.michaelrmossman.seasonal.presentation.MainListEvent
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveIconButton
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.icons.AdaptiveIcons
import io.github.alexzhirkevich.cupertino.adaptive.icons.Close
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.desc_bottom_sheet_close

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun BottomSheetTopBar(
    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit
) {
    AdaptiveIconButton(
        onClick = {
            onClick()
            onEvent(
                MainListEvent.SetCurrentHighlight(
                    hilt = null
                )
            )
        }
    ) {
        Icon(
            contentDescription = stringResource(
                Res.string.desc_bottom_sheet_close
            ),
            imageVector = AdaptiveIcons.Outlined.Close
        )
    }
}