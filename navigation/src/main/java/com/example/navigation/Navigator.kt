package com.example.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.ContentFlow -> navController.navigate(MainNavGraphDirections.actionGlobalContentFlow())
        is NavigationFlow.DetailFlow -> navController.navigate(MainNavGraphDirections.actionGlobalDetailFlow(navigationFlow.msg))
     }
}