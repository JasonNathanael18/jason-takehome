package com.example.navigation

sealed class NavigationFlow {
    object ContentFlow : NavigationFlow()
    class DetailFlow(val msg: String) : NavigationFlow()
}
