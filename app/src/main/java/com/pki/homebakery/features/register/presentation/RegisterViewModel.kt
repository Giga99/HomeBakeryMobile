package com.pki.homebakery.features.register.presentation

import com.pki.homebakery.features.register.data.RegisterService
import com.pki.homebakery.features.register.domain.RegisterInput
import com.pki.homebakery.features.register.presentation.RegisterViewModel.Effect
import com.pki.homebakery.features.register.presentation.RegisterViewModel.State
import com.pki.homebakery.ui.InputFieldError
import com.pki.homebakery.ui.InputFieldState
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import org.koin.android.annotation.KoinViewModel

private const val REGISTER_JOB = "REGISTER_JOB"

@KoinViewModel
class RegisterViewModel(
    private val registerService: RegisterService,
) : BaseViewModel<State, Effect>(State()) {

    fun onFullNameChange(fullName: String) =
        updateState { state.copy(fullName = state.fullName.withValue(fullName)) }

    fun onPhoneNumberChange(phoneNumber: String) =
        updateState { state.copy(phoneNumber = state.phoneNumber.withValue(phoneNumber)) }

    fun onAddressChange(address: String) =
        updateState { state.copy(address = state.address.withValue(address)) }

    fun onUsernameChange(username: String) =
        updateState { state.copy(username = state.username.withValue(username)) }

    fun onPasswordChange(password: String) =
        updateState { state.copy(password = state.password.withValue(password)) }

    fun onConfirmPasswordChange(confirmPassword: String) =
        updateState { state.copy(confirmPassword = state.confirmPassword.withValue(confirmPassword)) }

    fun register() = launchUniqueIfNotRunning(REGISTER_JOB) {
        if (
            !isFullNameFormatValid() || !isPhoneNumberFormatValid() || !isAddressFormatValid() ||
            !isUsernameFormatValid() || !isPasswordFormatValid() || !isConfirmPasswordFormatValid()
        ) {
            return@launchUniqueIfNotRunning
        }

        val input = RegisterInput(
            username = state.username.value,
            password = state.password.value,
            fullName = state.fullName.value,
            phoneNumber = state.phoneNumber.value,
            address = state.address.value,
        )
        updateState { state.copy(registerStatus = UIState.Loading()) }
        runCatching { registerService.register(input) }
            .onSuccess { effect(Effect.NavigateToHome) }
            .onFailure { updateState { state.copy(registerStatus = UIState.Failed(it)) } }
    }

    private fun isFullNameFormatValid() =
        if (state.fullName.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(fullName = state.fullName.withError(InputFieldError.InvalidFullNameFormat)) }
            false
        }

    private fun isPhoneNumberFormatValid() =
        if (state.phoneNumber.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(phoneNumber = state.phoneNumber.withError(InputFieldError.InvalidPhoneNumberFormat)) }
            false
        }

    private fun isAddressFormatValid() =
        if (state.address.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(address = state.address.withError(InputFieldError.InvalidAddressFormat)) }
            false
        }

    private fun isUsernameFormatValid() =
        if (state.username.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(username = state.username.withError(InputFieldError.InvalidUsernameFormat)) }
            false
        }

    private fun isPasswordFormatValid() =
        if (state.password.value.length >= 3) {
            true
        } else {
            updateState { state.copy(password = state.password.withError(InputFieldError.InvalidPasswordFormat)) }
            false
        }

    private fun isConfirmPasswordFormatValid() =
        if (state.confirmPassword.value == state.password.value) {
            true
        } else {
            updateState {
                state.copy(confirmPassword = state.confirmPassword.withError(InputFieldError.PasswordAndConfirmPasswordNotMatching))
            }
            false
        }

    data class State(
        val fullName: InputFieldState<String> = InputFieldState(""),
        val phoneNumber: InputFieldState<String> = InputFieldState(""),
        val address: InputFieldState<String> = InputFieldState(""),
        val username: InputFieldState<String> = InputFieldState(""),
        val password: InputFieldState<String> = InputFieldState(""),
        val confirmPassword: InputFieldState<String> = InputFieldState(""),
        val registerStatus: UIState<Unit> = UIState.Loaded(Unit),
    ) {
        val registerError = when {
            fullName.isInvalid -> fullName.error?.message
            phoneNumber.isInvalid -> phoneNumber.error?.message
            address.isInvalid -> address.error?.message
            username.isInvalid -> username.error?.message
            password.isInvalid -> password.error?.message
            confirmPassword.isInvalid -> confirmPassword.error?.message
            registerStatus.isFailed -> registerStatus.errorOrNull?.message
            else -> null
        }

        val isRegisterButtonEnabled = fullName.value.isNotBlank() &&
                phoneNumber.value.isNotBlank() &&
                address.value.isNotBlank() &&
                username.value.isNotBlank() &&
                password.value.isNotBlank() &&
                confirmPassword.value.isNotBlank()
    }

    sealed class Effect {
        data object NavigateToHome : Effect()
    }
}
