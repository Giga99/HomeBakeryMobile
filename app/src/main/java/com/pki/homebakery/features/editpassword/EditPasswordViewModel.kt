package com.pki.homebakery.features.editpassword

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.users
import com.pki.homebakery.features.editpassword.EditPasswordViewModel.Effect
import com.pki.homebakery.features.editpassword.EditPasswordViewModel.State
import com.pki.homebakery.features.profile.domain.ProfileInfo
import com.pki.homebakery.ui.InputFieldError
import com.pki.homebakery.ui.InputFieldState
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EditPasswordViewModel(
    private val prefsService: PrefsService,
) : BaseViewModel<State, Effect>(State()) {

    init {
        launchInViewModel {
            runCatching { prefsService.getCurrentUser() }
                .onSuccess { username ->
                    users.find { it.username == username }?.let { profileInfo ->
                        updateState {
                            state.copy(currentUser = profileInfo.asLoaded())
                        }
                    } ?: kotlin.run {
                        updateState { state.copy(currentUser = UIState.Failed(Exception("User not found"))) }
                    }
                }.onFailure {
                    updateState { state.copy(currentUser = it.asFailed()) }
                }
        }
    }

    fun onOldPasswordChange(oldPassword: String) =
        updateState { state.copy(oldPassword = state.oldPassword.withValue(oldPassword)) }

    fun onNewPasswordChange(newPassword: String) =
        updateState { state.copy(newPassword = state.newPassword.withValue(newPassword)) }

    fun onConfirmNewPasswordChange(confirmNewPassword: String) =
        updateState {
            state.copy(
                confirmNewPassword = state.confirmNewPassword.withValue(
                    confirmNewPassword
                )
            )
        }

    fun saveDetails() {
        if (!isOldPasswordFormatValid() || !isNewPasswordFormatValid() || !isConfirmNewPasswordFormatValid()) {
            return
        }

        users = users.map { profileInfo ->
            if (profileInfo.username == state.currentUser.valueOrNull?.username) {
                profileInfo.copy(password = state.newPassword.value)
            } else {
                profileInfo
            }
        }.toMutableList()

        effect(Effect.NavigateBack)
    }

    private fun isOldPasswordFormatValid() =
        if (state.oldPassword.value == state.currentUser.valueOrNull?.password) {
            true
        } else {
            updateState { state.copy(oldPassword = state.oldPassword.withError(InputFieldError.PasswordNotCorrectFormat)) }
            false
        }

    private fun isNewPasswordFormatValid() =
        if (state.newPassword.value.length >= 3) {
            true
        } else {
            updateState { state.copy(newPassword = state.newPassword.withError(InputFieldError.InvalidPasswordFormat)) }
            false
        }

    private fun isConfirmNewPasswordFormatValid() =
        if (state.confirmNewPassword.value == state.newPassword.value) {
            true
        } else {
            updateState {
                state.copy(confirmNewPassword = state.confirmNewPassword.withError(InputFieldError.PasswordAndConfirmPasswordNotMatching))
            }
            false
        }

    data class State(
        val currentUser: UIState<ProfileInfo> = UIState.Loading(),
        val oldPassword: InputFieldState<String> = InputFieldState(""),
        val newPassword: InputFieldState<String> = InputFieldState(""),
        val confirmNewPassword: InputFieldState<String> = InputFieldState(""),
    ) {
        val saveDetailsError = when {
            oldPassword.isInvalid -> oldPassword.error?.message
            newPassword.isInvalid -> newPassword.error?.message
            confirmNewPassword.isInvalid -> confirmNewPassword.error?.message
            else -> null
        }

        val isSaveDetailsButtonEnabled = oldPassword.value.isNotBlank() &&
                newPassword.value.isNotBlank() &&
                confirmNewPassword.value.isNotBlank()
    }

    sealed class Effect {
        data object NavigateBack : Effect()
    }
}
