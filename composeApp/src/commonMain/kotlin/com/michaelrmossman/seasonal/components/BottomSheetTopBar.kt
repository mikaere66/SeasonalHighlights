package com.michaelrmossman.seasonal.components

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
expect fun BottomSheetTopBar(
    onClick: () -> Unit,
    onEvent: (MainListEvent) -> Unit
)