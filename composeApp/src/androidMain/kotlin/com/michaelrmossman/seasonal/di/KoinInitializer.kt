package com.michaelrmossman.seasonal.di

import android.content.Context
import com.michaelrmossman.seasonal.di.databaseModule
import com.michaelrmossman.seasonal.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context
) {
    actual fun init() {
        startKoin {
            androidContext(context)
            modules(databaseModule, viewModelModule)
        }
    }
}