package com.pki.homebakery.features.home.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.navigation.NoParamsDestination

object HomeDestination : NoParamsDestination() {

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        HomeScreen()
    }
}
