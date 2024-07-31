package com.michaelrmossman.seasonal.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import com.michaelrmossman.seasonal.utils.iSiOS
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBarItem
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.icons.AdaptiveIcons
import io.github.alexzhirkevich.cupertino.adaptive.icons.Favorite
import io.github.alexzhirkevich.cupertino.adaptive.icons.FavoriteBorder
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.filled.Gearshape
import io.github.alexzhirkevich.cupertino.icons.filled.House
import io.github.alexzhirkevich.cupertino.icons.outlined.Gearshape
import io.github.alexzhirkevich.cupertino.icons.outlined.House
import org.jetbrains.compose.resources.stringResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.tab_fave
import seasonalhighlights.composeapp.generated.resources.tab_main
import seasonalhighlights.composeapp.generated.resources.tab_sett

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppBottomBar(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    val tabSelected by state.currentScreen.collectAsState()

    AdaptiveNavigationBar {
        val tabContent = listOf(
            stringResource(
                resource = Res.string.tab_main
            ) to when (iSiOS) {
                true -> when (tabSelected) {
                    Screen.Main -> CupertinoIcons.Filled.House
                    else -> CupertinoIcons.Outlined.House
                }
                else -> when (tabSelected) {
                    Screen.Main -> Icons.Filled.Home
                    else -> Icons.Outlined.Home
                }
            },
            stringResource(
                resource = Res.string.tab_fave
            ) to when (tabSelected) {
                Screen.Faves -> AdaptiveIcons.Outlined.Favorite
                else -> AdaptiveIcons.Outlined.FavoriteBorder
            },
            stringResource(
                resource = Res.string.tab_sett
            ) to when (iSiOS) {
                true -> when (tabSelected) {
                    Screen.Settings -> CupertinoIcons.Filled.Gearshape
                    else -> CupertinoIcons.Outlined.Gearshape
                }
                else -> when (tabSelected) {
                    Screen.Settings -> Icons.Filled.Settings
                    else -> Icons.Outlined.Settings
                }
            }
        )

        tabContent.forEachIndexed { index, pair ->
            AdaptiveNavigationBarItem(
                selected = tabSelected == Screen.entries[index],
                onClick = {
                    onEvent(
                        MainListEvent.SetCurrentScreen(
                            Screen.entries[index]
                        )
                    )
                    /* tabSelected will be updated in viewModel */
                },
                icon = {
                    Icon(
                        contentDescription = pair.first,
                        imageVector = pair.second
                    )
                },
                label = {
                    Text(pair.first)
                }
            )
        }
    }
}