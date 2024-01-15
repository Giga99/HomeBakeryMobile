package com.pki.homebakery.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.takeOrElse

val LocalColors = staticCompositionLocalOf<Colors> { error("Color palette is not initialized") }

inline val AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

data object BaseColors {
    val background = Color(0xFFFFFFFF)
    val text = Color(0xFF424242)
    val action = Color(0xFFF3CFBF)
    val error = Color(0xFFEB3223)
    val grey = Color(0xFFCACACA)
    val darkGrey = Color(0xFFA4A2A2)
}

@Immutable
data class Colors(
    val background: Color,
    val text: Color,
    val card: Color,
    val black: Color,
    val grey: Color,
    val darkGrey: Color,
    val white: Color,
    val action: Color,
    val error: Color,
) {
    val line = grey
    val cardOutline = grey.copy(0.2f)

    @Composable
    @ReadOnlyComposable
    fun on(color: Color) = when (color) {
        background -> text
        card -> text
        else -> if (color.isDark()) white else black
    }
}

fun getColorPalette(isDarkMode: Boolean) = Colors(
    background = BaseColors.background,
    text = BaseColors.text,
    card = BaseColors.background,
    black = BaseColors.text,
    grey = BaseColors.grey,
    darkGrey = BaseColors.darkGrey,
    white = BaseColors.background,
    action = BaseColors.action,
    error = BaseColors.error,
)

fun Color.isDark() = luminance() < 0.5f

fun Color.isLight() = luminance() >= 0.5f

fun Color.disabled() = copy(alpha = 0.5f)

@Composable
@ReadOnlyComposable
fun contentColorFor(backgroundColor: Color) =
    AppColors.on(backgroundColor).takeOrElse { LocalContentColor.current }

internal val dummyColorPalette = darkColorScheme(
    primary = Color.Magenta,
    onPrimary = Color.Magenta,
    primaryContainer = Color.Magenta,
    onPrimaryContainer = Color.Magenta,
    inversePrimary = Color.Magenta,
    secondary = Color.Magenta,
    onSecondary = Color.Magenta,
    secondaryContainer = Color.Magenta,
    onSecondaryContainer = Color.Magenta,
    tertiary = Color.Magenta,
    onTertiary = Color.Magenta,
    tertiaryContainer = Color.Magenta,
    onTertiaryContainer = Color.Magenta,
    background = Color.Magenta,
    onBackground = Color.Magenta,
    surface = Color.Magenta,
    onSurface = Color.Magenta,
    surfaceVariant = Color.Magenta,
    onSurfaceVariant = Color.Magenta,
    surfaceTint = Color.Magenta,
    inverseSurface = Color.Magenta,
    inverseOnSurface = Color.Magenta,
    error = Color.Magenta,
    onError = Color.Magenta,
    errorContainer = Color.Magenta,
    onErrorContainer = Color.Magenta,
    outline = Color.Magenta,
    outlineVariant = Color.Magenta,
    scrim = Color.Magenta,
)
