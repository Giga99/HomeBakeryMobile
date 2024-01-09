package com.pki.homebakery.features.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.features.home.presentation.HomeDestination
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Button
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.TextField
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppShapes
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsStateAndEffects
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen() {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<RegisterViewModel>()

    val state by viewModel.collectAsStateAndEffects { effect ->
        when (effect) {
            RegisterViewModel.Effect.NavigateToHome -> appNavigator.navigateTo(HomeDestination())
        }
    }

    RegisterContent(
        state = state,
        onFullNameChange = viewModel::onFullNameChange,
        onPhoneNumberChange = viewModel::onPhoneNumberChange,
        onAddressChange = viewModel::onAddressChange,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = viewModel::register,
        onLoginClick = appNavigator::navigateBack,
    )
}

@Composable
fun RegisterContent(
    state: RegisterViewModel.State,
    onFullNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = AppColors.action,
        statusBarColor = AppColors.action,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(R.drawable.ic_welcome),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 85.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AppColors.background,
                        AppShapes.extraLarge.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    )
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = state.fullName.value,
                    onValueChange = onFullNameChange,
                    placeholder = { Text(text = "Full Name", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_edit_text,
                    isError = state.fullName.isInvalid,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                )
                TextField(
                    value = state.phoneNumber.value,
                    onValueChange = onPhoneNumberChange,
                    placeholder = { Text(text = "Phone Number", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_phone,
                    isError = state.phoneNumber.isInvalid,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                TextField(
                    value = state.address.value,
                    onValueChange = onAddressChange,
                    placeholder = { Text(text = "Address", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_home,
                    isError = state.address.isInvalid,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                TextField(
                    value = state.username.value,
                    onValueChange = onUsernameChange,
                    placeholder = { Text(text = "Username", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_profile,
                    isError = state.username.isInvalid,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                TextField(
                    value = state.password.value,
                    onValueChange = onPasswordChange,
                    placeholder = { Text(text = "Password", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_lock,
                    isError = state.password.isInvalid,
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
                    value = state.confirmPassword.value,
                    onValueChange = onConfirmPasswordChange,
                    placeholder = { Text(text = "Confirm Password", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_lock,
                    isError = state.confirmPassword.isInvalid,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onRegisterClick()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                state.registerError?.let {
                    RegisterError(
                        errorMessage = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 48.dp),
                    )
                }
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if (state.registerError == null) 70.dp else 10.dp),
                    enabled = state.isRegisterButtonEnabled,
                    loading = state.registerStatus.isLoading,
                ) {
                    Text(text = "Register")
                }
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(
                            AppTypography.note.copy(
                                color = AppColors.darkGrey,
                                textDecoration = TextDecoration.Underline
                            ).toSpanStyle()
                        ) {
                            append("Log in")
                        }
                        append("!")
                    },
                    style = AppTypography.note,
                    color = AppColors.darkGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable(onClick = onLoginClick),
                )
            }
        }
    }
}

@Composable
private fun RegisterError(
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
private fun RegisterPreview() {
    PreviewView {
        RegisterContent(
            state = RegisterViewModel.State(),
            onFullNameChange = {},
            onPhoneNumberChange = {},
            onAddressChange = {},
            onUsernameChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onRegisterClick = {},
            onLoginClick = {},
        )
    }
}
