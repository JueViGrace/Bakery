package com.bakery.app

import android.app.Application
import com.bakery.app.di.appModule
import com.bakery.core.di.KoinBuilder
import com.bakery.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication

class BakeryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinBuilder(koinApplication())
            .addConfig(appDeclaration = {
                androidLogger(
                    level = if (BuildConfig.DEBUG) {
                        Level.DEBUG
                    } else {
                        Level.NONE
                    }
                )
                androidContext(this@BakeryApp)
            })
            .addModule(modules = coreModule())
            .addModule(module = appModule())
            .build()
    }
}
