package com.example.airqualityindex.shared.di.module

import com.example.airqualityindex.features.device.setup.viewmodel.HubViewModel
import com.example.airqualityindex.features.indoor.viewmodel.WeatherViewModel
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodel.AirQualityViewModel
import com.example.airqualityindex.features.main.viewmodel.UserViewModel
import org.koin.dsl.module

val viewModeModule = module {
    single { NavigationViewModel() }
    single { UserViewModel(get()) }
    single { AirQualityViewModel(get()) }
    single { HubViewModel(get(), get()) }
    single { WeatherViewModel(get()) }
}