plugins {
    // This is necessary to avoid plugins being loaded
    // multiple times in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    // alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    // alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.kotlinxSerialization).apply(false)
    // alias(libs.plugins.room).apply(false)
    // alias(libs.plugins.ksp).apply(false)
}