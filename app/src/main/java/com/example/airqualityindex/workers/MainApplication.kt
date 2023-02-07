package com.example.airqualityindex.workers

import android.app.Application
import android.util.Log
import com.example.airqualityindex.BuildConfig
import com.example.airqualityindex.di.repositoryModule
import com.example.airqualityindex.di.unitModules
import com.example.airqualityindex.di.viewModeModule
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication:Application() {
    private val TAG = MainApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(repositoryModule, viewModeModule, unitModules)
            )
        }

        RxJavaPlugins.setErrorHandler {
            Log.e(TAG, "rx java error handler: " + it.message )
        }
    }
}