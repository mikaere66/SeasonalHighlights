package com.michaelrmossman.seasonal.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

// COMPOSABLES

/* This is a temp workaround for the new JetBrains
   resources API, where line breaks and indents
   within string resources show as-is in the UI */
@Composable
fun trimmedStringResource(
    resource: StringResource
) : String {
    val string = stringResource(resource = resource)
    return when (string.contains("\n")) {
        false -> string
        else -> when (
            string.contains("  ") // Two spaces
        ) {
            false -> string.replace(
                "\n"," " // One space
            )
            else -> {
                val sections =
                    string.split("  ") // Two spaces
                val sb = StringBuilder()
                sections.forEach { section ->
                    sb.append(
                        section
                            // Line breaks with One space
                            .replace("\n"," ")
                             // Two spaces with One space
                            .replace("  "," ")
                    )
                }
                sb.toString()
            }
        }
    }
}

// EXTENSIONS

// https://stackoverflow.com/questions/67768746/chaining-modifier-based-on-certain-conditions-in-android-compose
// Adds a modifier (or not) based on a condition
fun Modifier.conditional(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}
