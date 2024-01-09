package com.pki.homebakery.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppShapes
import com.pki.homebakery.ui.theme.disabled
import com.pki.homebakery.ui.utils.elevatedShadow

@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.colors(),
    elevation: Dp = CardDefaults.elevation,
    borderColor: Color = CardDefaults.borderColor,
    border: BorderStroke? = CardDefaults.border(borderColor),
    content: @Composable ColumnScope.() -> Unit,
) {
    androidx.compose.material3.Card(
        // Temporary fix for card shadow until google adds global way
        modifier = modifier.elevatedShadow(
            elevation = elevation,
            shape = shape,
        ),
        shape = shape,
        colors = colors,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            focusedElevation = elevation,
            hoveredElevation = elevation,
            draggedElevation = elevation,
            disabledElevation = elevation,
        ),
        border = border,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Card(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.colors(),
    elevation: Dp = CardDefaults.elevation,
    borderColor: Color = CardDefaults.borderColor,
    border: BorderStroke? = CardDefaults.border(borderColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    androidx.compose.material3.Card(
        onClick = onClick,
        // Temporary fix for card shadow until google adds global way
        modifier = modifier.elevatedShadow(
            elevation = elevation,
            shape = shape,
        ),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation,
            focusedElevation = elevation,
            hoveredElevation = elevation,
            draggedElevation = elevation,
            disabledElevation = elevation,
        ),
        border = border,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun FlatCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.colors(),
    borderColor: Color = CardDefaults.borderColor,
    border: BorderStroke? = CardDefaults.border(borderColor),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = 0.dp,
        border = border,
        content = content,
    )
}

@Composable
fun FlatCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.colors(),
    borderColor: Color = CardDefaults.borderColor,
    border: BorderStroke? = CardDefaults.border(borderColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = 0.dp,
        border = border,
        interactionSource = interactionSource,
        content = content,
    )
}

object CardDefaults {
    val shape
        @Composable
        @ReadOnlyComposable
        get() = AppShapes.large

    val elevation
        @Composable
        @ReadOnlyComposable
        get() = 6.dp

    val borderColor
        @Composable
        @ReadOnlyComposable
        get() = AppColors.cardOutline

    @Composable
    fun border(color: Color, width: Dp = Dp.Hairline) = BorderStroke(width, color)

    @Composable
    fun colors(
        containerColor: Color = AppColors.card,
        contentColor: Color = AppColors.text,
        disabledContentColor: Color = contentColor.disabled(),
    ) = androidx.compose.material3.CardDefaults.cardColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = containerColor,
        disabledContentColor = disabledContentColor,
    )
}

@ScreenPreviews
@Composable
private fun CardPreview() {
    PreviewView {
        Column(modifier = Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Regular Card")
                }
            }
            Card(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.colors(
                    containerColor = AppColors.action,
                    contentColor = AppColors.white,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Regular Card With Click")
                }
            }
            Card(
                onClick = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Regular Card With Click Disabled")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            FlatCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Flat Card")
                }
            }
            FlatCard(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.colors(
                    containerColor = AppColors.action,
                    contentColor = AppColors.white,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Flat Card With Click")
                }
            }
            FlatCard(
                onClick = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text("Flat Card With Click Disabled")
                }
            }
        }
    }
}
