package com.example.airqualityindex.features.main.services

interface NavigationCallback {
    fun onShowMenu()

    fun onInvisibleBottomNavigation()

    fun onLogout()

    fun onPressBack()

    fun onItemOnSelected()
}