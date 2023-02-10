package com.example.airqualityindex.shared.di.module

import com.example.airqualityindex.features.device.setup.viewmodels.HubViewModel
import com.example.airqualityindex.features.indoor.viewmodel.WeatherForecastViewModel
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodels.AirQualityViewModel
import com.example.airqualityindex.features.user.viewmodel.UserViewModel
import org.koin.dsl.module

val viewModeModule = module {
    single { NavigationViewModel() }
    single { UserViewModel(get()) }
    single { AirQualityViewModel(get()) }
    single { HubViewModel(get(), get()) }
    single { WeatherForecastViewModel(get()) }
}