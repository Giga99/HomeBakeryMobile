package com.pki.homebakery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LocalSystemUiController = staticCompositionLocalOf<SystemUiController> {
    error("LocalSystemUiController is not initialized")
}

@Composable
fun HomeBakeryTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = getColorPalette(isDarkMode)
    val typography = remember { Typography.get() }
    val systemUiController = rememberSystemUiController()
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colorScheme.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkMode
//        }
//    }

    MaterialTheme(
        colorScheme = dummyColorPalette,
        typography = dummyTypography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalColors provides colors,
            LocalContentColor provides colors.text,
            LocalTypography provides typography,
            LocalTextStyle provides typography.body,
            LocalSystemUiController provides systemUiController,
        ) {
            content()
        }
    }
}