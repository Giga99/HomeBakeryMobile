package com.pki.homebakery.features.splash.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.navigation.NoParamsDestination

data object SplashDestination : NoParamsDestination() {

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        SplashScreen()
    }
}
