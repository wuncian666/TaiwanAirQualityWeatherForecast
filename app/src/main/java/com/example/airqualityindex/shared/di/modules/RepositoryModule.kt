package com.example.airqualityindex.shared.di.modules

import com.example.airqualityindex.shared.repositories.implement.*
import org.koin.dsl.module

val repositoryModule = module {
    single { UserDataRepositoryImpl(get()) }
    single { AirQualityRepositoryImpl(get()) }
    single { SearchSensorThingsRepositoryImpl(get()) }
    single { WeatherForecastRepositoryImpl(get()) }
    single { HubRepositoryImpl() }
}