package com.pki.homebakery.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.elevatedShadow(
    elevation: Dp,
    shape: Shape,
) = if (elevation > 0.dp) {
    this.shadow(
        elevation = elevation,
        shape = shape,
        spotColor = DefaultShadowColor.copy(0.3f),
    )
} else {
    this
}
