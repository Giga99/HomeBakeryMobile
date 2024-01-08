package com.pki.homebakery.features.register.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.navigation.NoParamsDestination

data object RegisterDestination : NoParamsDestination() {

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        RegisterScreen()
    }
}
