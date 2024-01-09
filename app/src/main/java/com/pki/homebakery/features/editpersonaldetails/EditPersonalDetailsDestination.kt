package com.pki.homebakery.features.editpersonaldetails

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.navigation.NoParamsDestination

data object EditPersonalDetailsDestination : NoParamsDestination() {

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        EditPersonalDetailsScreen()
    }
}
