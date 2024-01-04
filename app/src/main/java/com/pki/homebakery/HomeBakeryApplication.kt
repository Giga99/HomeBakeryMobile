package com.pki.homebakery

import android.app.Application
import com.pki.homebakery.di.GeneratedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

class HomeBakeryApplication : Application() {

    override fun onCreate() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@HomeBakeryApplication)
            modules(
                GeneratedModule().module,
            )
        }
        super.onCreate()
    }
}
