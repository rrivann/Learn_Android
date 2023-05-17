package com.dicoding.mysubmissionapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.mysubmissionapp.ui.components.MyTopBar
import com.dicoding.mysubmissionapp.ui.navigation.Screen
import com.dicoding.mysubmissionapp.ui.screen.detail.DetailScreen
import com.dicoding.mysubmissionapp.ui.screen.favorite.FavoriteScreen
import com.dicoding.mysubmissionapp.ui.screen.home.HomeScreen
import com.dicoding.mysubmissionapp.ui.screen.profile.ProfileScreen

@Composable
fun CryptoApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute == Screen.Home.route) {
                MyTopBar(onProfileClick = {
                    navHostController.navigate(Screen.Profile.route)
                }, onFavoriteClick = { navHostController.navigate(Screen.Favorite.route) })
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = {
                    navHostController.navigate(
                        Screen.DetailScreen.createRoute(
                            it.id
                        )
                    )
                })
            }
            composable(
                route = Screen.DetailScreen.route,
                arguments = listOf(navArgument("cryptoId") { type = NavType.LongType })
            )
            {
                val id = it.arguments?.getLong("cryptoId") ?: -1L
                DetailScreen(
                    cryptoId = id,
                    navigateBack = { navHostController.navigateUp() }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onBackClick = { navHostController.navigateUp() })
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    onBackClick = { navHostController.navigateUp() },
                    navigateToDetail = {
                        navHostController.navigate(
                            Screen.DetailScreen.createRoute(
                                it.id
                            )
                        )
                    })
            }
        }
    }
}
