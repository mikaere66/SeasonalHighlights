package com.michaelrmossman.seasonal

import android.app.Application
import com.michaelrmossman.seasonal.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        KoinInitializer(applicationContext).init()
    }
}