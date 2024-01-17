package com.pki.homebakery.features.addcomment.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pki.homebakery.navigation.DestinationParam
import com.pki.homebakery.navigation.ParameterizedDestination
import com.pki.homebakery.navigation.getDestinationParam

private const val CAKE_ID = "cakeId"
private const val CAKE_TITLE = "cakeTitle"

data object AddCommentDestination : ParameterizedDestination(
    namedNavArguments = listOf(
        navArgument(CAKE_ID) { type = NavType.StringType },
        navArgument(CAKE_TITLE) { type = NavType.StringType },
    )
) {

    operator fun invoke(cakeId: String, cakeTitle: String): String =
        buildRouteWithParams(
            DestinationParam(CAKE_ID, cakeId),
            DestinationParam(CAKE_TITLE, cakeTitle)
        )

    @Composable
    override fun Screen(navBackStackEntry: NavBackStackEntry) {
        val cakeId = navBackStackEntry.getDestinationParam(CAKE_ID)
        val cakeTitle = navBackStackEntry.getDestinationParam(CAKE_TITLE)
        AddCommentScreen(cakeId, cakeTitle)
    }
}