package com.michaelrmossman.seasonal.cache

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {

    fun createDriver(): SqlDriver
}