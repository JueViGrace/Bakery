package com.bakery.core.database.driver

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bakery.core.database.BakeryCliDb

actual class DriverFactory(
    private val context: Context
) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = BakeryCliDb.Schema.synchronous(),
            context = context,
            name = "bakery.db"
        )
    }
}
