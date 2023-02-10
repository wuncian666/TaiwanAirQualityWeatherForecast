package com.example.airqualityindex.shared.di

import android.app.Application
import android.util.Log
import com.example.airqualityindex.shared.di.module.repositoryModule
import com.example.airqualityindex.shared.di.module.utilModule
import com.example.airqualityindex.shared.di.module.viewModeModule
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(repositoryModule, viewModeModule, utilModule)
            )
        }

        RxJavaPlugins.setErrorHandler {
            Log.e("MainApplication", "rx java error handler: " + it.message)
        }
    }
}