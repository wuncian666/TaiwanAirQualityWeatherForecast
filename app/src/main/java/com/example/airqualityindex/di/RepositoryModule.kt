package com.example.airqualityindex.di

import com.example.airqualityindex.repository.*
import com.example.airqualityindex.shared.SharedPreferencesManager
import org.koin.dsl.module

val repositoryModule = module {
    single { UserDataRepositoryImpl(get()) }
    single { AQIRepositoryImpl(get()) }
    single { PerHourAirQualityRepositoryImpl(get()) }
    single { SearchSensorThingsRepositoryImpl(get()) }
    single { WeatherForecastRepositoryImpl(get()) }
    single { HubRepositoryImpl() }
    single { SharedPreferencesManager(get()) }
}