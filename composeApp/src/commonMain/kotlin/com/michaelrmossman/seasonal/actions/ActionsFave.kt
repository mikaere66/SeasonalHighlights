package com.michaelrmossman.seasonal.actions

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import com.michaelrmossman.seasonal.utils.trimmedStringResource
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveAlertDialog
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.icons.AdaptiveIcons
import io.github.alexzhirkevich.cupertino.adaptive.icons.Delete
import io.github.alexzhirkevich.cupertino.cancel
import io.github.alexzhirkevich.cupertino.default
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.desc_faves_remove
import seasonalhighlights.composeapp.generated.resources.dialog_cancel
import seasonalhighlights.composeapp.generated.resources.dialog_okay
import seasonalhighlights.composeapp.generated.resources.faves_dialog_text
import seasonalhighlights.composeapp.generated.resources.faves_dialog_title

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun ActionsFave(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    var alertVisible by remember {
        mutableStateOf(false)
    }

    IconButton(
        enabled = state.favourites.isNotEmpty(),
        onClick = { alertVisible = true }
    ) {
        Icon(
            contentDescription = stringResource(
                Res.string.desc_faves_remove
            ),
            imageVector = AdaptiveIcons.Outlined.Delete
        )
    }

    if (alertVisible) {
        val favesCount = state.favourites.size
        val dialogTitle = pluralStringResource(
            resource = Res.plurals.faves_dialog_title,
            quantity = favesCount,
            formatArgs = arrayOf(favesCount)
        )

        AdaptiveAlertDialog(
            onDismissRequest = { alertVisible = false },
            title = { Text(dialogTitle) },
            message = {
                Text(
                    trimmedStringResource(
                        Res.string.faves_dialog_text
                    )
                )
            }
        ) {
            cancel(
                onClick = { alertVisible = false }
            ) {
                Text(
                    stringResource(
                        Res.string.dialog_cancel
                    )
                )
            }
            default(
                onClick = {
                    alertVisible = false
                    onEvent(
                        MainListEvent.DeleteAllFavourites
                    )
                }
            ) {
                Text(
                    stringResource(
                        Res.string.dialog_okay
                    )
                )
            }
        }
    }
}