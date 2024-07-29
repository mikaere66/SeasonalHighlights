package com.michaelrmossman.seasonal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.michaelrmossman.seasonal.components.AppSubtitle
import com.michaelrmossman.seasonal.components.AppTopBar
import com.michaelrmossman.seasonal.enums.Screen
import com.michaelrmossman.seasonal.presentation.MainListEvent
import com.michaelrmossman.seasonal.presentation.MainScreenState
import com.michaelrmossman.seasonal.presentation.MainViewModel
import com.michaelrmossman.seasonal.tabs.MainListTab
import com.michaelrmossman.seasonal.tabs.SettingsTab
import com.michaelrmossman.seasonal.theme.AppTheme
import com.michaelrmossman.seasonal.utils.iSiOS
import io.github.aakira.napier.Napier
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveNavigationBarItem
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveWidget
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.icons.AdaptiveIcons
import io.github.alexzhirkevich.cupertino.adaptive.icons.Favorite
import io.github.alexzhirkevich.cupertino.adaptive.icons.FavoriteBorder
import io.github.alexzhirkevich.cupertino.adaptive.icons.Menu
import io.github.alexzhirkevich.cupertino.adaptive.icons.Person
import io.github.alexzhirkevich.cupertino.adaptive.icons.Settings
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.filled.House
import io.github.alexzhirkevich.cupertino.icons.outlined.House
import io.github.alexzhirkevich.cupertino.icons.filled.Gearshape
import io.github.alexzhirkevich.cupertino.icons.outlined.Gearshape
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.currentKoinScope
import seasonalhighlights.composeapp.generated.resources.app_alias
import seasonalhighlights.composeapp.generated.resources.baseline_arrow_back_24
import seasonalhighlights.composeapp.generated.resources.baseline_filter_list_24
import seasonalhighlights.composeapp.generated.resources.baseline_filter_list_off_24
import seasonalhighlights.composeapp.generated.resources.baseline_more_vert_24
import seasonalhighlights.composeapp.generated.resources.baseline_radio_button_checked_24
import seasonalhighlights.composeapp.generated.resources.baseline_radio_button_unchecked_24
import seasonalhighlights.composeapp.generated.resources.desc_menu_overflow
import seasonalhighlights.composeapp.generated.resources.menu_all_seasons
import seasonalhighlights.composeapp.generated.resources.menu_filter_title
import seasonalhighlights.composeapp.generated.resources.Res
import seasonalhighlights.composeapp.generated.resources.setting_navigate_back
import seasonalhighlights.composeapp.generated.resources.tab_fave
import seasonalhighlights.composeapp.generated.resources.tab_main
import seasonalhighlights.composeapp.generated.resources.tab_sett

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppBottomBar(
    onEvent: (MainListEvent) -> Unit,
    state: MainScreenState
    
) {
    AdaptiveNavigationBar {
        var tabSelected by rememberSaveable {
            mutableStateOf(0)
        }
        val tabContent = listOf(
            stringResource(
                resource = Res.string.tab_main
            ) to when (iSiOS) {
                true -> when (tabSelected) {
                    0 -> CupertinoIcons.Filled.House
                    else -> CupertinoIcons.Outlined.House
                }
                else -> when (tabSelected) {
                    0 -> Icons.Filled.Home
                    else -> Icons.Outlined.Home
                }
            },
            stringResource(
                resource = Res.string.tab_fave
            ) to when (tabSelected) {
                1 -> AdaptiveIcons.Outlined.Favorite
                else -> AdaptiveIcons.Outlined.FavoriteBorder
            },
            stringResource(
                resource = Res.string.tab_sett
            ) to when (iSiOS) {
                true -> when (tabSelected) {
                    2 -> CupertinoIcons.Filled.Gearshape
                    else -> CupertinoIcons.Outlined.Gearshape
                }
                else -> when (tabSelected) {
                    2 -> Icons.Filled.Settings
                    else -> Icons.Outlined.Settings
                }
            }
        )

        tabContent.forEachIndexed { index, pair ->
            AdaptiveNavigationBarItem(
                selected = tabSelected == index,
                onClick = {
                    tabSelected = index // TODO
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