package com.example.airqualityindex.shared.di.module

import com.example.airqualityindex.shared.util.UserDataManager
import org.koin.dsl.module

val utilModule = module {
    single { UserDataManager(get()) }
}