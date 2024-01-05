package com.pki.homebakery.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.contentColorFor

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = AppColors.card,
    contentColor: Color = contentColorFor(color),
    elevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        shadowElevation = elevation,
        border = border,
        content = content,
    )
}

@Composable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = AppColors.card,
    contentColor: Color = contentColorFor(color),
    elevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        shadowElevation = elevation,
        border = border,
        interactionSource = interactionSource,
        content = content,
    )
}
