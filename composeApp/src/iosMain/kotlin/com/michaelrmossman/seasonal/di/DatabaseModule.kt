package com.michaelrmossman.seasonal.di

import com.michaelrmossman.seasonal.cache.IOSDatabaseDriverFactory
import com.michaelrmossman.seasonal.cache.DatabaseImpl
import com.michaelrmossman.seasonal.utils.JsonUtils
import org.koin.dsl.module

actual val databaseModule = module {
    single<JsonUtils> { JsonUtils() }

    single<DatabaseImpl> {
        DatabaseImpl(
            databaseDriverFactory = IOSDatabaseDriverFactory(
            ) , json = get()
        )
    }
}