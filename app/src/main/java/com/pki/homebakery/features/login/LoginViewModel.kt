package com.pki.homebakery.features.login

import com.pki.homebakery.features.login.LoginViewModel.Effect
import com.pki.homebakery.features.login.LoginViewModel.State
import com.pki.homebakery.ui.InputFieldState
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel : BaseViewModel<State, Effect>(State()) {

    fun onUsernameChange(username: String) =
        updateState { state.copy(username = state.username.withValue(username)) }

    fun onPasswordChange(password: String) =
        updateState { state.copy(password = state.password.withValue(password)) }

    fun login() {}

    data class State(
        val username: InputFieldState<String> = InputFieldState(""),
        val password: InputFieldState<String> = InputFieldState(""),
        val loginStatus: UIState<Unit> = UIState.Loaded(Unit),
    ) {
        val loginError = when {
            username.isInvalid -> username.error?.message
            password.isInvalid -> password.error?.message
            else -> null
        }

        val isLoginButtonEnabled = username.value.isNotBlank() && password.value.isNotBlank()
    }

    sealed class Effect {
        data object NavigateToHome : Effect()
    }
}
