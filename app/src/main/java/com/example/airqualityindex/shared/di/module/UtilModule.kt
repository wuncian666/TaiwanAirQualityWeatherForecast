package com.example.airqualityindex.shared.di.module

import com.example.airqualityindex.shared.util.LoadingViewUtil
import com.example.airqualityindex.shared.util.SystemTimeManager
import com.example.airqualityindex.shared.util.ToastViewUtil
import com.example.airqualityindex.shared.util.UserDataManager
import org.koin.dsl.module

val utilModule = module {
    single { UserDataManager(get()) }
    single { SystemTimeManager() }
    single { ToastViewUtil() }
    single { LoadingViewUtil() }
}