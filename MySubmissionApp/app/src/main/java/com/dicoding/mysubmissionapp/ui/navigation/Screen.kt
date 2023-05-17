package com.dicoding.mysubmissionapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailScreen : Screen("home/{cryptoId}") {
        fun createRoute(cryptoId: Long) = "home/$cryptoId"
    }

    object Profile : Screen("profile")
    object Favorite : Screen("favorite")
}