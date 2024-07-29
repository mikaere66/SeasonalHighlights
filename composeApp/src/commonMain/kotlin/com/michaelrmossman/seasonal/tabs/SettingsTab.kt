package com.michaelrmossman.seasonal.tabs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveSwitch
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.setting_save_restore_season

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun SettingsTab(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    val checked = remember {
        mutableStateOf(
            state.saveAndRestoreSeason
        )
    }

    LazyColumn {
        item(key = 0) { // TODO
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .toggleable(
                        value = checked.value,
                        role = Role.Switch,
                        onValueChange = { value ->
                            checked.value = value
                            onEvent(
                                MainListEvent.SetShouldSaveSeason(
                                    save = value
                                )
                            )
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = stringResource(
                        Res.string.setting_save_restore_season
                    ),
                )
                AdaptiveSwitch(
                    checked = checked.value,
                    onCheckedChange = { value ->
                        checked.value = value
                        onEvent(
                            MainListEvent.SetShouldSaveSeason(
                                save = value
                            )
                        )
                    }
                )
            }
        }
    }
}