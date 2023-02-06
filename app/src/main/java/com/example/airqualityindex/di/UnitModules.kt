package com.example.airqualityindex.di

import com.example.airqualityindex.workers.NavigationCallbackImpl
import org.koin.dsl.module

val unitModules = module{
    single { NavigationCallbackImpl() }
}