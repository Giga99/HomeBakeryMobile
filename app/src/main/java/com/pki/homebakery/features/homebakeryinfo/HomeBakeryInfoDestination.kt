package com.pki.homebakery.features.homebakeryinfo

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.navigation.NoParamsDestination

data object HomeBakeryInfoDestination : NoParamsDestination() {

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        HomeBakeryInfoScreen()
    }
}
