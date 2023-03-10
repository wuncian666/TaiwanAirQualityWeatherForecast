package com.example.airqualityindex.shared.di.module

import com.example.airqualityindex.shared.repository.implement.*
import org.koin.dsl.module

val repositoryModule = module {
    single { UserDataRepositoryImpl(get(), get()) }
    single { AirQualityRepositoryImpl(get()) }
    single { SensorRepositoryImpl(get()) }
    single { WeatherRepositoryImpl(get()) }
    single { HubRepositoryImpl() }
}