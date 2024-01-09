package com.pki.homebakery.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.pki.homebakery.R

val LocalTypography = staticCompositionLocalOf<Typography> { error("Typography not initialized") }

inline val AppTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current

private val LatoFontFamily = FontFamily(
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_regular, FontWeight.Normal),
)

private val SansitaFontFamily = FontFamily(
    Font(R.font.sansita_bold, FontWeight.Bold),
    Font(R.font.sansita_regular, FontWeight.Normal),
)

@Immutable
data class Typography(
    val body: TextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        lineHeightStyle = lineHeightStyle,
        platformStyle = platformStyle,
    ),
    val bodyBold: TextStyle = body.copy(fontWeight = FontWeight.Bold),
    val h1: TextStyle = TextStyle(
        fontFamily = SansitaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 46.sp,
        lineHeight = 28.sp,
        lineHeightStyle = lineHeightStyle,
        platformStyle = platformStyle,
    ),
    val h1Bold: TextStyle = h1.copy(fontWeight = FontWeight.Bold),
    val h2: TextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        lineHeightStyle = lineHeightStyle,
        platformStyle = platformStyle,
    ),
    val h2Bold: TextStyle = h2.copy(fontWeight = FontWeight.Bold),
    val h3: TextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        lineHeightStyle = lineHeightStyle,
        platformStyle = platformStyle,
    ),
    val h3Bold: TextStyle = h3.copy(fontWeight = FontWeight.Bold),
    val note: TextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
        lineHeightStyle = lineHeightStyle,
        platformStyle = platformStyle,
    ),
    val noteBold: TextStyle = note.copy(fontWeight = FontWeight.Bold),
    val button: TextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        lineHeightStyle = lineHeightStyle,
        platformStyle = platformStyle,
    ),
) {
    companion object {
        @Stable
        fun get() = Typography()
    }
}

private val lineHeightStyle =
    LineHeightStyle(LineHeightStyle.Alignment.Center, LineHeightStyle.Trim.None)

private val platformStyle = PlatformTextStyle(null, PlatformParagraphStyle(false))

internal val dummyTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(fontSize = 60.sp),
    displayMedium = TextStyle(fontSize = 60.sp),
    displaySmall = TextStyle(fontSize = 60.sp),
    headlineLarge = TextStyle(fontSize = 60.sp),
    headlineMedium = TextStyle(fontSize = 60.sp),
    headlineSmall = TextStyle(fontSize = 60.sp),
    titleLarge = TextStyle(fontSize = 60.sp),
    titleMedium = TextStyle(fontSize = 60.sp),
    titleSmall = TextStyle(fontSize = 60.sp),
    bodyLarge = TextStyle(fontSize = 60.sp),
    bodyMedium = TextStyle(fontSize = 60.sp),
    bodySmall = TextStyle(fontSize = 60.sp),
    labelLarge = TextStyle(fontSize = 60.sp),
    labelMedium = TextStyle(fontSize = 60.sp),
    labelSmall = TextStyle(fontSize = 60.sp),
)
