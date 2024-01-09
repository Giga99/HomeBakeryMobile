package com.pki.homebakery.features.editpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Button
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.TextField
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsStateAndEffects
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditPasswordScreen() {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<EditPasswordViewModel>()

    val state by viewModel.collectAsStateAndEffects { effect ->
        when (effect) {
            EditPasswordViewModel.Effect.NavigateBack -> appNavigator.navigateBack()
        }
    }

    EditPasswordContent(
        state = state,
        onBackClick = appNavigator::navigateBack,
        onOldPasswordChange = viewModel::onOldPasswordChange,
        onNewPasswordChange = viewModel::onNewPasswordChange,
        onConfirmNewPasswordChange = viewModel::onConfirmNewPasswordChange,
        onSaveDetailsClick = viewModel::saveDetails,
    )
}

@Composable
private fun EditPasswordContent(
    state: EditPasswordViewModel.State,
    onBackClick: () -> Unit,
    onOldPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmNewPasswordChange: (String) -> Unit,
    onSaveDetailsClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = { TopAppBar(title = "Edit Personal Details", onBackPressed = onBackClick) },
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = state.oldPassword.value,
                onValueChange = onOldPasswordChange,
                placeholder = { Text(text = "Old Password", style = AppTypography.body) },
                leadingIcon = R.drawable.ic_lock,
                isError = state.oldPassword.isInvalid,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )
            TextField(
                value = state.newPassword.value,
                onValueChange = onNewPasswordChange,
                placeholder = { Text(text = "New Password", style = AppTypography.body) },
                leadingIcon = R.drawable.ic_lock,
                isError = state.newPassword.isInvalid,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )
            TextField(
                value = state.confirmNewPassword.value,
                onValueChange = onConfirmNewPasswordChange,
                placeholder = { Text(text = "Confirm Password", style = AppTypography.body) },
                leadingIcon = R.drawable.ic_lock,
                isError = state.confirmNewPassword.isInvalid,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onSaveDetailsClick()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            state.saveDetailsError?.let {
                SaveDetailsError(
                    errorMessage = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                )
            }
            Button(
                onClick = onSaveDetailsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (state.saveDetailsError == null) 70.dp else 10.dp,
                        bottom = 16.dp
                    ),
                enabled = state.isSaveDetailsButtonEnabled,
            ) {
                Text(text = "Save Password")
            }
        }
    }
}

@Composable
private fun SaveDetailsError(
    errorMessage: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = null,
            tint = AppColors.error,
        )
        Text(
            text = errorMessage,
            style = AppTypography.body,
            color = AppColors.error,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}

@Composable
@ScreenPreviews
private fun EditPasswordPreview() {
    PreviewView {
        EditPasswordContent(
            state = EditPasswordViewModel.State(),
            onBackClick = {},
            onOldPasswordChange = {},
            onNewPasswordChange = {},
            onConfirmNewPasswordChange = {},
            onSaveDetailsClick = {},
        )
    }
}
