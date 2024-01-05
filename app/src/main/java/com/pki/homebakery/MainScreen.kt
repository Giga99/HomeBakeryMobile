package com.pki.homebakery

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pki.homebakery.features.login.LoginDestination
import com.pki.homebakery.navigation.AppNavigator
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.navigation.NavHost
import com.pki.homebakery.navigation.NavigationIntent
import com.pki.homebakery.navigation.allDestinations
import com.pki.homebakery.navigation.composable
import com.pki.homebakery.ui.theme.HomeBakeryTheme
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun HomeScreen() {
    val navHostController = rememberNavController()
    val appNavigator = AppNavigator()

    CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
        NavigationEffects(
            appNavigator = appNavigator,
            navHostController = navHostController,
        )
        HomeBakeryTheme(isDarkMode = true) {
            NavHost(
                navController = navHostController,
                startDestination = LoginDestination,
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

@Composable
fun NavigationEffects(
    appNavigator: AppNavigator,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, appNavigator, navHostController) {
        appNavigator.navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}
