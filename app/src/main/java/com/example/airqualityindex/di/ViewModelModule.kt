package com.example.airqualityindex.di

import com.example.airqualityindex.viewmodels.HubViewModel
import com.example.airqualityindex.viewmodels.*
import org.koin.dsl.module

val viewModeModule = module {
    single { AQIListViewModel(get()) }
    single { PerHourAirQualityViewModel(get()) }
    single { SearchSensorThingsViewModel(get()) }
    single { HubViewModel(get(), get()) }
    single { WeatherForecastViewModel(get()) }
}