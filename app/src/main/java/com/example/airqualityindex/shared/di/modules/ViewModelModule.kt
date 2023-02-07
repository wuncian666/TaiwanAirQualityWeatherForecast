package com.example.airqualityindex.di

import com.example.airqualityindex.features.indoor.viewmodels.WeatherForecastViewModel
import com.example.airqualityindex.features.outdoor.viewmodels.PerHourAirQualityViewModel
import com.example.airqualityindex.features.device.setup.viewmodels.HubViewModel
import com.example.airqualityindex.viewmodels.*
import org.koin.dsl.module

val viewModeModule = module {
    single { PerHourAirQualityViewModel(get()) }
    single { SearchSensorThingsViewModel(get()) }
    single { HubViewModel(get(), get()) }
    single { WeatherForecastViewModel(get()) }
}