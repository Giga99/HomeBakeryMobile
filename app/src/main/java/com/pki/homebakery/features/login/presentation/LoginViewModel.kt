package com.pki.homebakery.features.login.presentation

import com.pki.homebakery.features.login.data.LoginService
import com.pki.homebakery.features.login.presentation.LoginViewModel.Effect
import com.pki.homebakery.features.login.presentation.LoginViewModel.State
import com.pki.homebakery.ui.InputFieldError
import com.pki.homebakery.ui.InputFieldState
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import org.koin.android.annotation.KoinViewModel

private const val LOGIN_JOB = "LOGIN_JOB"

@KoinViewModel
class LoginViewModel(
    private val loginService: LoginService,
) : BaseViewModel<State, Effect>(State()) {

    fun onUsernameChange(username: String) =
        updateState { state.copy(username = state.username.withValue(username)) }

    fun onPasswordChange(password: String) =
        updateState { state.copy(password = state.password.withValue(password)) }

    fun login() = launchUniqueIfNotRunning(LOGIN_JOB) {
        if (!isUsernameFormatValid() || !isPasswordFormatValid()) {
            return@launchUniqueIfNotRunning
        }

        val username = state.username.value
        val password = state.password.value
        updateState { state.copy(loginStatus = UIState.Loading()) }
        runCatching { loginService.login(username, password) }
            .onSuccess { effect(Effect.NavigateToHome) }
            .onFailure { updateState { state.copy(loginStatus = UIState.Failed(it)) } }
    }

    private fun isUsernameFormatValid() =
        if (state.username.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(username = state.username.withError(InputFieldError.InvalidUsernameFormat)) }
            false
        }

    private fun isPasswordFormatValid() =
        if (state.password.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(password = state.password.withError(InputFieldError.InvalidPasswordFormat)) }
            false
        }

    data class State(
        val username: InputFieldState<String> = InputFieldState(""),
        val password: InputFieldState<String> = InputFieldState(""),
        val loginStatus: UIState<Unit> = UIState.Loaded(Unit),
    ) {
        val loginError = when {
            username.isInvalid -> username.error?.message
            password.isInvalid -> password.error?.message
            loginStatus.isFailed -> loginStatus.errorOrNull?.message
            else -> null
        }

        val isLoginButtonEnabled = username.value.isNotBlank() && password.value.isNotBlank()
    }

    sealed class Effect {
        data object NavigateToHome : Effect()
    }
}
