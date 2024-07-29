import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.kotlinxSerialization)
    // alias(libs.plugins.ksp)
    // alias(libs.plugins.room)
    alias(libs.plugins.sqlDelight) version libs.versions.sqlDelight.get()
}

kotlin {
//    sourceSets.commonMain {
//        kotlin.srcDir("build/generated/ksp/metadata") // New from PicSplash
//    }

    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                    freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_17}")
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            binaryOption("bundleId", "com.michaelrmossman.seasonal.Shared")
            isStatic = false // Was false
            // Required when using NativeSQLiteDriver
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            // implementation(libs.kotlinx.coroutines.android) // Was commented out

            implementation(libs.androidx.compose.material3)
            implementation(libs.androidx.compose.material3.window)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.sqldelight.driver.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            // implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            // implementation(libs.androidx.navigation)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            implementation(libs.cupertino.adaptive)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            // implementation(libs.kotlinx.coroutines.core) // Was commented out
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            // implementation(libs.kstore.file)
            implementation(libs.napier)
            implementation(libs.navigation.compose)
            // implementation(libs.multiplatform.settings)
            // implementation(libs.room.runtime)
            // implementation(libs.sqlite.bundled)
            // implementation(libs.sqlite.framework)
            implementation(libs.sqldelight.coroutines.ext)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.driver.native)
        }
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.michaelrmossman.seasonal"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }

    namespace = "com.michaelrmossman.seasonal"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
}

//kotlin.sourceSets.commonMain {
//    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
//}

//room {
//    schemaDirectory("$projectDir/schemas")
//}

//dependencies {
//    // https://slack-chats.kotlinlang.org/t/18855762/hello-guys-i-am-trying-to-set-up-room-db-for-kmm-and-it-work
//    // https://github.com/cvivek07/KMM-PicSplash
//    implementation(libs.androidx.navigation.runtime.ktx)
//    implementation(libs.androidx.lifecycle.viewmodel.android)
//    // Replace this with: ksp(libs.androidx.room.compiler) when it's stable
//    add("kspCommonMainMetadata", libs.room.compiler)
//}

// https://github.com/JetBrains/compose-multiplatform/issues/4928
// https://kotlinlang.org/docs/gradle-compiler-options.html
//tasks
//    .withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>()
//    .configureEach {
//        compilerOptions {
//            if (name != "kspCommonMainKotlinMetadata") {
//               dependsOn("kspCommonMainKotlinMetadata")
//            }
//        }
//    }

sqldelight {
    databases {
        create("SeasonalDatabase") {
            // https://cashapp.github.io/sqldelight
            packageName.set("com.michaelrmossman.seasonal.cache")
        }
    }
}