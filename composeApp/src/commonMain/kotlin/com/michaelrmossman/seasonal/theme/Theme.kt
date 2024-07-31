package com.michaelrmossman.seasonal.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.CupertinoThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.MaterialThemeSpec
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.alexzhirkevich.cupertino.theme.darkColorScheme
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

expect fun determineTheme(): Theme

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppTheme(
    theme: Theme = determineTheme(),
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    AdaptiveTheme(
        content = content,
        cupertino = CupertinoThemeSpec.Default(
            colorScheme = if (useDarkTheme) {
                darkColorScheme()
            } else {
                lightColorScheme()
            }
        ),
        material = MaterialThemeSpec.Default().copy(
            colorScheme = if (useDarkTheme) {
                androidx.compose.material3.darkColorScheme()
            } else {
                androidx.compose.material3.lightColorScheme()
            }
        ),
        target = theme
    )
}