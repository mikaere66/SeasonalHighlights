package com.michaelrmossman.seasonal.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

class IOSDatabaseDriverFactory: DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            SeasonalDatabase.Schema,"seasonal.db"
        )
    }
}