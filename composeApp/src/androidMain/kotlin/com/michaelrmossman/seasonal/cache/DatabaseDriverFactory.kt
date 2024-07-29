package com.michaelrmossman.seasonal.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

class AndroidDatabaseDriverFactory(
    private val context: Context
) : DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            SeasonalDatabase.Schema, context,"seasonal.db"
        )
    }
}