package com.michaelrmossman.seasonal.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.michaelrmossman.seasonal.actions.ActionsFave
import com.michaelrmossman.seasonal.actions.ActionsMain
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import io.github.alexzhirkevich.cupertino.CupertinoNavigateBackButton
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.app_alias
import seasonalhighlights.composeapp.generated.resources.baseline_arrow_back_24
import seasonalhighlights.composeapp.generated.resources.desc_navigate_back

@OptIn(ExperimentalAdaptiveApi::class, ExperimentalCupertinoApi::class)
@Composable
fun AppTopBar(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
) {
    AdaptiveTopAppBar(
        actions = {
            when (state.currentScreen.value) {
                Screen.Main -> ActionsMain(
                    onEvent = onEvent,
                    state = state
                )
                Screen.Faves -> ActionsFave(
                    onEvent = onEvent,
                    state = state
                )
                Screen.Settings -> { /* Not yet implemented */ }
            }
        },
        // https://stackoverflow.com/questions/69192042/how-to-use-jetpack-compose-app-bar-backbutton/70409412#70409412
        navigationIcon = {
            if (
                state.currentScreen.value != Screen.Main
            ) {
                val backTextOrDesc = stringResource(
                    resource = Res.string.desc_navigate_back
                )
                AdaptiveWidget(
                    cupertino = {
                        CupertinoNavigateBackButton(
                            onClick = {
                                onEvent(
                                    MainListEvent.SetCurrentScreen(
                                        screen = Screen.Main
                                    )
                                )
                            },
                        ) {
                            CupertinoText(backTextOrDesc)
                        }
                    },
                    material = {
                        IconButton(
                            onClick = {
                                onEvent(
                                    MainListEvent.SetCurrentScreen(
                                        screen = Screen.Main
                                    )
                                )
                            }
                        ) {
                            Icon(
                                contentDescription = backTextOrDesc,
                                imageVector = vectorResource(
                                    Res.drawable.baseline_arrow_back_24
                                )
                            )
                        }
                    }
                )
            }
        },
        title = {
            AdaptiveWidget(
                cupertino = { // TODO
                    Text(
                        text = stringResource(Res.string.app_alias)
                    )
                },
                material = {
                    Text(
                        text = stringResource(Res.string.app_alias),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            )
        }
    )
}