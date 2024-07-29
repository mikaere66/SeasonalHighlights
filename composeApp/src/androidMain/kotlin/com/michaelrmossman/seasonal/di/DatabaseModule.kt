package com.michaelrmossman.seasonal.di

import com.michaelrmossman.seasonal.cache.AndroidDatabaseDriverFactory
import com.michaelrmossman.seasonal.cache.DatabaseImpl
import com.michaelrmossman.seasonal.utils.JsonUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val databaseModule = module {
    single<JsonUtils> { JsonUtils() }

    single<DatabaseImpl> {
        DatabaseImpl(
            databaseDriverFactory = AndroidDatabaseDriverFactory(
                androidContext()
            ) , json = get()
        )
    }
}