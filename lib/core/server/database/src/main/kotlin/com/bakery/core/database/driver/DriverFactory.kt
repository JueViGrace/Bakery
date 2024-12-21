package com.bakery.core.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.bakery.core.database.BakerySvDb

class DriverFactory {
    suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:bakery.db")
        BakerySvDb.Schema.awaitCreate(driver)
        return driver
    }
}
