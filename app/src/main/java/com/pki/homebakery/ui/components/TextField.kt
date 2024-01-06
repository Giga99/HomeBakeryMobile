package com.pki.homebakery.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pki.homebakery.ui.utils.elevatedShadow
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppShapes
import com.pki.homebakery.ui.theme.AppTypography

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    @DrawableRes leadingIcon: Int? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    androidx.compose.material3.TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.elevatedShadow(10.dp, AppShapes.extraLarge),
        enabled = enabled,
        textStyle = AppTypography.body,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon?.let {
            {
                Icon(painter = painterResource(it), contentDescription = null)
            }
        },
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = 1,
        interactionSource = interactionSource,
        shape = AppShapes.extraLarge,
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppColors.darkGrey,
            unfocusedTextColor = AppColors.darkGrey,
            disabledTextColor = AppColors.grey,
            errorTextColor = AppColors.error,
            focusedContainerColor = AppColors.white,
            unfocusedContainerColor = AppColors.white,
            disabledContainerColor = AppColors.white,
            errorContainerColor = AppColors.white,
            cursorColor = AppColors.darkGrey,
            errorCursorColor = AppColors.error,
            focusedIndicatorColor = AppColors.background,
            unfocusedIndicatorColor = AppColors.background,
            disabledIndicatorColor = AppColors.background,
            errorIndicatorColor = AppColors.background,
            focusedLeadingIconColor = AppColors.darkGrey,
            unfocusedLeadingIconColor = AppColors.darkGrey,
            disabledLeadingIconColor = AppColors.grey,
            errorLeadingIconColor = AppColors.darkGrey,
            focusedTrailingIconColor = AppColors.darkGrey,
            unfocusedTrailingIconColor = AppColors.darkGrey,
            disabledTrailingIconColor = AppColors.grey,
            errorTrailingIconColor = AppColors.darkGrey,
            focusedLabelColor = AppColors.grey,
            unfocusedLabelColor = AppColors.grey,
            disabledLabelColor = AppColors.grey,
            errorLabelColor = AppColors.grey,
            focusedPlaceholderColor = AppColors.grey,
            unfocusedPlaceholderColor = AppColors.grey,
            disabledPlaceholderColor = AppColors.grey,
            errorPlaceholderColor = AppColors.grey,
            focusedSupportingTextColor = AppColors.grey,
            unfocusedSupportingTextColor = AppColors.grey,
            disabledSupportingTextColor = AppColors.grey,
            errorSupportingTextColor = AppColors.grey,
        ),
    )
}
