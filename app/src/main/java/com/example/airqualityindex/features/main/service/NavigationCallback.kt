package com.example.airqualityindex.features.main.service

interface NavigationCallback {
    fun onShowMenu()

    fun onInvisibleBottomNavigation()

    fun onLogout()

    fun onPressBack()

    fun onItemOnSelected()
}