package com.example.airqualityindex.features.main.viewmodels

import androidx.lifecycle.ViewModel
import com.example.airqualityindex.features.main.services.NavigationCallback

class NavigationViewModel : ViewModel() {
    var navigationCallback: NavigationCallback? = null
}