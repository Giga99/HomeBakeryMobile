package com.pki.homebakery.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp

inline val AppShapes
    @Composable
    @ReadOnlyComposable
    get() = MaterialTheme.shapes

private val ShapesExtraSmallCornerSize = 8.dp
private val ShapesSmallCornerSize = 12.dp
private val ShapesMediumCornerSize = 16.dp
private val ShapesLargeCornerSize = 20.dp
private val ShapesExtraLargeCornerSize = 24.dp

internal val Shapes = Shapes(
    extraSmall = RoundedCornerShape(ShapesExtraSmallCornerSize),
    small = RoundedCornerShape(ShapesSmallCornerSize),
    medium = RoundedCornerShape(ShapesMediumCornerSize),
    large = RoundedCornerShape(ShapesLargeCornerSize),
    extraLarge = RoundedCornerShape(ShapesExtraLargeCornerSize),
)
