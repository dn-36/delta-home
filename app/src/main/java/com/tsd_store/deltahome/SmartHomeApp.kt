package com.tsd_store.deltahome

import android.app.Application
import com.tsd_store.deltahome.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.GlobalContext.startKoin

class SmartHomeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            AndroidLogger()          // можно убрать на релизе
            androidContext(this@SmartHomeApp)
            modules(
                appModule
            )
        }
    }
}