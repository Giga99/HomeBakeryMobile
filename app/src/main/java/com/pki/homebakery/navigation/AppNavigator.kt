package com.pki.homebakery.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalAppNavigator =
    staticCompositionLocalOf<AppNavigator> { error("AppNavigator is not initialized") }

class AppNavigator(private val navController: NavController) {

    fun navigateBack(route: String? = null, inclusive: Boolean = false) {
        if (route != null) {
            navController.popBackStack(route, inclusive)
        } else {
            navController.popBackStack()
        }
    }

    fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        isInclusive: Boolean = false,
        isSingleTop: Boolean = false,
    ) {
        navController.navigate(route) {
            launchSingleTop = isSingleTop
            popUpToRoute?.let { popUpToRoute ->
                popUpTo(popUpToRoute) { inclusive = isInclusive }
            }
        }
    }
}
