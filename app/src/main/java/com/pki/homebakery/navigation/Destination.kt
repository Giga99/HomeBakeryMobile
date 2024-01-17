package com.pki.homebakery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import com.pki.homebakery.features.addcomment.presentation.AddCommentDestination
import com.pki.homebakery.features.details.presentation.CakeDetailsDestination
import com.pki.homebakery.features.editpassword.EditPasswordDestination
import com.pki.homebakery.features.editpersonaldetails.EditPersonalDetailsDestination
import com.pki.homebakery.features.home.presentation.HomeDestination
import com.pki.homebakery.features.homebakeryinfo.presentation.HomeBakeryInfoDestination
import com.pki.homebakery.features.login.presentation.LoginDestination
import com.pki.homebakery.features.register.presentation.RegisterDestination
import com.pki.homebakery.features.settings.presentation.SettingsDestination
import com.pki.homebakery.features.splash.presentation.SplashDestination

val allDestinations = listOf(
    SplashDestination,
    LoginDestination,
    RegisterDestination,
    HomeDestination,
    SettingsDestination,
    EditPersonalDetailsDestination,
    EditPasswordDestination,
    HomeBakeryInfoDestination,
    CakeDetailsDestination,
    AddCommentDestination,
)

abstract class Destination(
    namedNavArguments: List<NamedNavArgument> = emptyList()
) {
    private val hasParams: Boolean = namedNavArguments.isNotEmpty()

    protected val route: String = this::class.qualifiedName.orEmpty()

    val fullRoute: String = if (hasParams) {
        val builder = StringBuilder("$route?")
        namedNavArguments.forEach { namedNavArgument ->
            builder.append("${namedNavArgument.name}={${namedNavArgument.name}}&")
        }
        builder.removeSuffix("&").toString()
    } else {
        route
    }

    @Composable
    abstract fun Screen(navBackStackEntry: NavBackStackEntry)
}

abstract class NoParamsDestination : Destination() {
    operator fun invoke(): String = route
}

abstract class ParameterizedDestination(
    namedNavArguments: List<NamedNavArgument>
) : Destination(namedNavArguments) {

    protected fun buildRouteWithParams(vararg params: DestinationParam): String {
        var routeWithParams = fullRoute
        params.forEach { param ->
            routeWithParams = routeWithParams.replace("{${param.name}}", param.value)
        }
        return routeWithParams
    }
}

data class DestinationParam(
    val name: String,
    val value: String,
)

fun NavBackStackEntry.getDestinationParam(paramKey: String): String {
    return arguments?.getString(paramKey) ?: error("No param found")
}
