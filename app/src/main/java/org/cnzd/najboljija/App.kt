package org.cnzd.najboljija

import android.app.Application
import org.cnzd.najboljija.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                    listOf(
                            networkinModule,
                            sharedPrefsModule,
                            viewModels,
                            serviceModule,
                            commonModule)
            )
        }
    }
}