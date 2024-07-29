package com.michaelrmossman.seasonal.di

import com.michaelrmossman.seasonal.di.databaseModule
import com.michaelrmossman.seasonal.di.viewModelModule
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(databaseModule, viewModelModule)
        }
    }
}