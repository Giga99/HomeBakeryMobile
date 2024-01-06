package com.pki.homebakery.features.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.ui.InputFieldState
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
fun LoginScreen() {
    val viewModel = koinViewModel<LoginViewModel>()

    val state by viewModel.collectAsStateAndEffects { effect ->
        when (effect) {
            LoginViewModel.Effect.NavigateToHome -> TODO()
        }
    }

    LoginContent(
        state = state,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLogin = viewModel::login,
    )
}

@Composable
fun LoginContent(
    state: LoginViewModel.State,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
) {
    Scaffold(
        containerColor = AppColors.action,
        statusBarColor = AppColors.action,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(
                        AppColors.background,
                        AppShapes.extraLarge.copy(
                            bottomEnd = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp)
                        )
                    )
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = state.username.value,
                    onValueChange = onUsernameChange,
                    placeholder = { Text(text = "Username", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_profile,
                    isError = state.username.isInvalid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                )
                TextField(
                    value = state.password.value,
                    onValueChange = onPasswordChange,
                    placeholder = { Text(text = "Password", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_lock,
                    isError = state.password.isInvalid,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                state.loginError?.let {
                    Text(
                        text = it,
                        style = AppTypography.note,
                        color = AppColors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Button(
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isLoginButtonEnabled,
                ) {
                    Text(text = "Login")
                }
                Text(
                    text = "New here? Register!",
                    style = AppTypography.note,
                    color = AppColors.darkGrey,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun LoginError(
    errorMessage: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(painter = painterResource(R.drawable.ic_error), contentDescription = null)
        Text(
            text = errorMessage,
            style = AppTypography.body,
            color = AppColors.error,
        )
    }
}

@Composable
@ScreenPreviews
private fun LoginPreview() {
    PreviewView {
        LoginContent(
            state = LoginViewModel.State(
                username = InputFieldState(""),
                password = InputFieldState(""),
            ),
            onUsernameChange = {},
            onPasswordChange = {},
            onLogin = {},
        )
    }
}
