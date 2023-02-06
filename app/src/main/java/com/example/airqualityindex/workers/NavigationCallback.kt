package com.example.airqualityindex.workers

interface NavigationCallback {
    fun onShowMenu()

    fun onInvisibleBottomNavigation()

    fun onLogout()

    fun onPressBack()

    fun onItemOnSelected()
}