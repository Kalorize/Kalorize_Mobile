package com.kalorize.kalorizeappmobile.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object OnBoarding : Screen("onboarding_screen")
    object Login : Screen("login")
    object Home : Screen("home")
}