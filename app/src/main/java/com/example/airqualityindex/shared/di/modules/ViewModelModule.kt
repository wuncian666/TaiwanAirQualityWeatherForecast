package com.example.airqualityindex.shared.di.modules

import com.example.airqualityindex.features.device.setup.viewmodels.HubViewModel
import com.example.airqualityindex.features.indoor.viewmodels.WeatherForecastViewModel
import com.example.airqualityindex.features.main.viewmodels.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodels.AirQualityViewModel
import org.koin.dsl.module

val viewModeModule = module {
    single { NavigationViewModel() }
    single { AirQualityViewModel(get()) }
    single { HubViewModel(get(), get()) }
    single { WeatherForecastViewModel(get()) }
}