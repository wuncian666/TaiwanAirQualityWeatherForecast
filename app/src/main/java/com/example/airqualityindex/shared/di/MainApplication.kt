package com.example.airqualityindex.shared.di

import android.app.Application
import android.util.Log
import com.example.airqualityindex.shared.di.modules.repositoryModule
import com.example.airqualityindex.shared.di.modules.viewModeModule
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
                listOf(repositoryModule, viewModeModule)
            )
        }

        RxJavaPlugins.setErrorHandler {
            Log.e("MainApplication", "rx java error handler: " + it.message)
        }
    }
}