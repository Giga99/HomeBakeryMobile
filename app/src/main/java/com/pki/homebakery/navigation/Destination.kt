package com.pki.homebakery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.features.login.presentation.LoginDestination
import com.pki.homebakery.features.splash.presentation.SplashDestination
import kotlinx.serialization.json.Json

const val DESTINATION_PARAM_KEY = "PARAM"

val allDestinations = listOf(
    SplashDestination,
    LoginDestination,
)

abstract class Destination {
    private val hasParams: Boolean = this is ParameterizedDestination<*>

    protected val route: String = this::class.qualifiedName.orEmpty()

    val fullRoute: String = if (hasParams) {
        "$route/{$DESTINATION_PARAM_KEY}"
    } else {
        route
    }

    @Composable
    abstract fun Screen(navBackStackEntry: NavBackStackEntry)
}

abstract class NoParamsDestination : Destination() {
    operator fun invoke(): String = route
}

abstract class ParameterizedDestination<T : DestinationParam> : Destination() {
    operator fun invoke(param: T): String {
        val paramJson = encodeParam(param)
        return "$route/$paramJson"
    }

    abstract fun encodeParam(param: T): String
}

interface DestinationParam

inline fun <reified T : DestinationParam> NavBackStackEntry.getDestinationParam(): T {
    val paramJson =
        arguments?.getString(DESTINATION_PARAM_KEY) ?: throw IllegalStateException("No param found")
    return Json.decodeFromString<T>(paramJson)
}
