package com.tsd_store.deltahome

import android.app.Application
import com.tsd_store.deltahome.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)

            modules(
                appModule
            )
        }
    }
}