package com.pki.homebakery.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.R
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppShapes
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.theme.disabled

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    minSize: DpSize = DpSize(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight),
    accentColor: Color = AppColors.action,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = accentColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    buttonIconPosition: ButtonIconPosition = ButtonIconPosition.Start,
    content: @Composable RowScope.() -> Unit,
) {
    androidx.compose.material3.Button(
        onClick = onClickThrottled { if (!loading) onClick() },
        modifier = modifier.defaultMinSize(minWidth = minSize.width, minHeight = minSize.height),
        enabled = enabled || loading,
        shape = ButtonDefaults.Shape,
        border = null,
        colors = colors,
        elevation = ButtonDefaults.elevation(defaultElevation = 4.dp, pressedElevation = 10.dp),
        contentPadding = ButtonDefaults.ContentPadding,
        interactionSource = interactionSource,
        content = {
            CompositionLocalProvider(LocalTextStyle provides AppTypography.button) {
                ButtonContent(
                    contentColor = LocalContentColor.current,
                    loading = loading,
                    iconResId = iconResId,
                    iconTint = iconTint,
                    buttonIconPosition = buttonIconPosition,
                    content = content,
                )
            }
        },
    )
}

@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    minSize: DpSize = DpSize(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight),
    accentColor: Color = AppColors.action,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(contentColor = accentColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    buttonIconPosition: ButtonIconPosition = ButtonIconPosition.Start,
    content: @Composable RowScope.() -> Unit,
) {
    androidx.compose.material3.OutlinedButton(
        onClick = onClickThrottled { if (!loading) onClick() },
        modifier = modifier.defaultMinSize(minWidth = minSize.width, minHeight = minSize.height),
        enabled = enabled || loading,
        shape = ButtonDefaults.Shape,
        border = ButtonDefaults.outlinedBorder.copy(brush = SolidColor(if (enabled || loading) accentColor else accentColor.disabled())),
        colors = colors,
        contentPadding = ButtonDefaults.ContentPadding,
        interactionSource = interactionSource,
        content = {
            CompositionLocalProvider(LocalTextStyle provides AppTypography.button) {
                ButtonContent(
                    contentColor = LocalContentColor.current,
                    loading = loading,
                    iconResId = iconResId,
                    iconTint = iconTint,
                    buttonIconPosition = buttonIconPosition,
                    content = content,
                )
            }
        },
    )
}

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    minSize: DpSize = DpSize(ButtonDefaults.MinWidth, ButtonDefaults.MinHeight),
    accentColor: Color = AppColors.action,
    colors: ButtonColors = ButtonDefaults.textButtonColors(contentColor = accentColor),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    buttonIconPosition: ButtonIconPosition = ButtonIconPosition.Start,
    content: @Composable RowScope.() -> Unit,
) {
    androidx.compose.material3.TextButton(
        onClick = onClickThrottled { if (!loading) onClick() },
        modifier = modifier.defaultMinSize(minWidth = minSize.width, minHeight = minSize.height),
        enabled = enabled,
        shape = ButtonDefaults.Shape,
        colors = colors,
        contentPadding = ButtonDefaults.ContentPadding,
        interactionSource = interactionSource,
        content = {
            CompositionLocalProvider(LocalTextStyle provides AppTypography.button) {
                ButtonContent(
                    contentColor = LocalContentColor.current,
                    loading = loading,
                    iconResId = iconResId,
                    iconTint = iconTint,
                    buttonIconPosition = buttonIconPosition,
                    content = content,
                )
            }
        },
    )
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    minSize: Dp = IconButtonDefaults.minSize,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    androidx.compose.material3.IconButton(
        onClick = onClick.throttled(),
        modifier = modifier.defaultMinSize(minWidth = minSize, minHeight = minSize),
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(),
        interactionSource = interactionSource,
        content = {
            CompositionLocalProvider(
                LocalContentColor provides if (enabled) AppColors.text else AppColors.text.disabled(),
                LocalTextStyle provides AppTypography.note,
            ) {
                content()
            }
        },
    )
}

@Composable
fun IconButton(
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
    tint: Color = AppColors.action,
    minSize: Dp = IconButtonDefaults.minSize,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = minSize, minHeight = minSize),
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(IconButtonDefaults.iconSize),
            tint = tint,
        )
    }
}

@Composable
private fun RowScope.ButtonContent(
    contentColor: Color,
    loading: Boolean,
    @DrawableRes iconResId: Int?,
    iconTint: Color? = null,
    buttonIconPosition: ButtonIconPosition,
    content: @Composable RowScope.() -> Unit,
) {
    if (loading) {
        if (LocalInspectionMode.current) {
            CircularProgressIndicator(
                progress = 0.8f,
                modifier = Modifier.size(24.dp),
                color = contentColor,
                strokeWidth = 1.dp,
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = contentColor,
                strokeWidth = 1.dp,
            )
        }
    } else {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        ) {
            if (iconResId != null) {
                when (buttonIconPosition) {
                    ButtonIconPosition.Start -> {
                        Icon(
                            painter = painterResource(iconResId),
                            contentDescription = null,
                            tint = iconTint ?: contentColor,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .size(ButtonDefaults.IconSize),
                        )
                    }

                    ButtonIconPosition.End -> Unit
                }
            }

            Row(
                modifier = Modifier.weight(weight = 1f, fill = false),
                horizontalArrangement = Arrangement.Center,
                content = content,
            )

            if (iconResId != null) {
                when (buttonIconPosition) {
                    ButtonIconPosition.Start -> Unit

                    ButtonIconPosition.End -> {
                        Icon(
                            painter = painterResource(iconResId),
                            contentDescription = null,
                            tint = iconTint ?: contentColor,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .size(ButtonDefaults.IconSize),
                        )
                    }
                }
            }
        }
    }
}

enum class ButtonIconPosition { Start, End }

object ButtonDefaults {
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 16.dp
    private val OutlinedBorderSize = 1.dp

    val MinWidth = 200.dp
    val MinHeight = 40.dp
    val IconSize = 22.dp
    val ContentPadding = PaddingValues(
        horizontal = ButtonHorizontalPadding,
        vertical = ButtonVerticalPadding,
    )

    val Shape @Composable get() = AppShapes.extraLarge

    val outlinedBorder: BorderStroke
        @Composable get() = BorderStroke(
            width = OutlinedBorderSize,
            brush = SolidColor(AppColors.background),
        )

    @Composable
    fun buttonColors(
        containerColor: Color = AppColors.action,
        contentColor: Color = AppColors.text,
        disabledContainerColor: Color = AppColors.darkGrey,
        disabledContentColor: Color = AppColors.white,
    ): ButtonColors = androidx.compose.material3.ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonColors(
        contentColor: Color = AppColors.action,
        disabledContentColor: Color = contentColor.disabled(),
    ): ButtonColors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textButtonColors(
        contentColor: Color = AppColors.action,
        disabledContentColor: Color = contentColor.disabled(),
    ): ButtonColors = androidx.compose.material3.ButtonDefaults.textButtonColors(
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun elevation(
        defaultElevation: Dp = 2.dp,
        pressedElevation: Dp = 8.dp,
        disabledElevation: Dp = 0.dp,
        hoveredElevation: Dp = 4.dp,
        focusedElevation: Dp = 4.dp,
    ): ButtonElevation = androidx.compose.material3.ButtonDefaults.elevatedButtonElevation(
        defaultElevation = defaultElevation,
        pressedElevation = pressedElevation,
        disabledElevation = disabledElevation,
        hoveredElevation = hoveredElevation,
        focusedElevation = focusedElevation,
    )
}

object IconButtonDefaults {
    val minSize = 32.dp
    val iconSize = 16.dp

    @Composable
    fun iconButtonColors(
        containerColor: Color = AppColors.background,
        contentColor: Color = AppColors.action,
        disabledContainerColor: Color = containerColor.disabled(),
        disabledContentColor: Color = contentColor.disabled(),
    ): IconButtonColors = androidx.compose.material3.IconButtonDefaults.iconButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )
}

@ScreenPreviews
@Composable
private fun OutlinedButtonPreview() {
    PreviewView {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Regular OutlinedButton")
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                iconResId = R.drawable.ic_call_answer,
            ) {
                Text("Regular OutlinedButton Start Icon")
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                iconResId = R.drawable.ic_call_answer,
                buttonIconPosition = ButtonIconPosition.End,
            ) {
                Text("Regular OutlinedButton End Icon")
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                enabled = false,
            ) {
                Text("Regular OutlinedButton Disabled")
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                loading = true,
            ) {
                Text("Regular OutlinedButton Loading")
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun TextButtonPreview() {
    PreviewView {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Regular TextButton")
            }
            TextButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                iconResId = R.drawable.ic_call_answer,
            ) {
                Text("Regular TextButton Start Icon")
            }
            TextButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                iconResId = R.drawable.ic_call_answer,
                buttonIconPosition = ButtonIconPosition.End,
            ) {
                Text("Regular TextButton End Icon")
            }
            TextButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                enabled = false,
            ) {
                Text("Regular TextButton Disabled")
            }
            TextButton(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                loading = true,
            ) {
                Text("Regular TextButton Disabled")
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun ButtonPreview() {
    PreviewView {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Regular Button")
            }
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                iconResId = R.drawable.ic_call_answer,
            ) {
                Text("Regular Button Start Icon")
            }
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                iconResId = R.drawable.ic_call_answer,
                buttonIconPosition = ButtonIconPosition.End,
            ) {
                Text("Regular Button End Icon")
            }
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                enabled = false,
            ) {
                Text("Regular Button Disabled")
            }
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp),
                loading = true,
            ) {
                Text("Regular Button Loading")
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun IconButtonPreview() {
    PreviewView {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(
                onClick = {},
                iconResId = R.drawable.ic_call_answer,
            )
            IconButton(
                onClick = {},
                iconResId = R.drawable.ic_call_answer,
                enabled = false,
            )
        }
    }
}
