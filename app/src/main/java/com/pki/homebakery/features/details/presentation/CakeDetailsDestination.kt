package com.pki.homebakery.features.details.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pki.homebakery.navigation.DestinationParam
import com.pki.homebakery.navigation.ParameterizedDestination
import com.pki.homebakery.navigation.getDestinationParam

private const val CAKE_ID = "cakeId"

data object CakeDetailsDestination : ParameterizedDestination(
    namedNavArguments = listOf(
        navArgument(CAKE_ID) { type = NavType.StringType }
    )
) {

    operator fun invoke(cakeId: String): String =
        buildRouteWithParams(DestinationParam(CAKE_ID, cakeId))

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        val cakeId = navBackStackEntry.getDestinationParam(CAKE_ID)
        CakeDetailsScreen(cakeId)
    }
}
