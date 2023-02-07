package com.example.airqualityindex.shared.di.modules

import com.example.airqualityindex.shared.database.SharedPreferencesManager
import com.example.airqualityindex.shared.repositories.implement.*
import org.koin.dsl.module

val repositoryModule = module {
    single { UserDataRepositoryImpl(get()) }
    single { PerHourAirQualityRepositoryImpl(get()) }
    single { SearchSensorThingsRepositoryImpl(get()) }
    single { WeatherForecastRepositoryImpl(get()) }
    single { HubRepositoryImpl() }
    single { SharedPreferencesManager(get()) }
}