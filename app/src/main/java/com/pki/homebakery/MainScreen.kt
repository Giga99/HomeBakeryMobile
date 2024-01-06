package com.pki.homebakery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.pki.homebakery.features.splash.presentation.SplashDestination
import com.pki.homebakery.navigation.AppNavigator
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.navigation.NavHost
import com.pki.homebakery.navigation.allDestinations
import com.pki.homebakery.navigation.composable
import com.pki.homebakery.ui.theme.HomeBakeryTheme

@Composable
fun HomeScreen() {
    val navHostController = rememberNavController()
    val appNavigator = AppNavigator(navHostController)

    CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
        HomeBakeryTheme(isDarkMode = true) {
            NavHost(
                navController = navHostController,
                startDestination = SplashDestination,
            ) {
                allDestinations.forEach { destination ->
                    composable(destination) {
                        destination.Screen(it)
                    }
                }
            }
        }
    }
}
