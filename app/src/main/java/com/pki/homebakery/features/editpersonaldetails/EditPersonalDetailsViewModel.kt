package com.pki.homebakery.features.editpersonaldetails

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.users
import com.pki.homebakery.features.editpersonaldetails.EditPersonalDetailsViewModel.Effect
import com.pki.homebakery.features.editpersonaldetails.EditPersonalDetailsViewModel.State
import com.pki.homebakery.features.profile.domain.ProfileInfo
import com.pki.homebakery.ui.InputFieldError
import com.pki.homebakery.ui.InputFieldState
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EditPersonalDetailsViewModel(
    private val prefsService: PrefsService,
) : BaseViewModel<State, Effect>(State()) {

    init {
        launchInViewModel {
            runCatching { prefsService.getCurrentUser() }
                .onSuccess { username ->
                    users.find { it.username == username }?.let { profileInfo ->
                        updateState {
                            state.copy(
                                currentUser = profileInfo.asLoaded(),
                                fullName = state.fullName.withValue(profileInfo.fullName),
                                phoneNumber = state.phoneNumber.withValue(profileInfo.phoneNumber),
                                address = state.address.withValue(profileInfo.address),
                            )
                        }
                    } ?: kotlin.run {
                        updateState { state.copy(currentUser = UIState.Failed(Exception("User not found"))) }
                    }
                }.onFailure {
                    updateState { state.copy(currentUser = it.asFailed()) }
                }
        }
    }

    fun onFullNameChange(fullName: String) =
        updateState { state.copy(fullName = state.fullName.withValue(fullName)) }

    fun onPhoneNumberChange(phoneNumber: String) =
        updateState { state.copy(phoneNumber = state.phoneNumber.withValue(phoneNumber)) }

    fun onAddressChange(address: String) =
        updateState { state.copy(address = state.address.withValue(address)) }

    fun saveDetails() {
        if (!isFullNameFormatValid() || !isPhoneNumberFormatValid() || !isAddressFormatValid()) {
            return
        }

        users = users.map { profileInfo ->
            if (profileInfo.username == state.currentUser.valueOrNull?.username) {
                profileInfo.copy(
                    fullName = state.fullName.value,
                    phoneNumber = state.phoneNumber.value,
                    address = state.address.value,
                )
            } else {
                profileInfo
            }
        }.toMutableList()

        effect(Effect.NavigateBack)
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

    data class State(
        val currentUser: UIState<ProfileInfo> = UIState.Loading(),
        val fullName: InputFieldState<String> = InputFieldState(currentUser.valueOrNull?.fullName.orEmpty()),
        val phoneNumber: InputFieldState<String> = InputFieldState(currentUser.valueOrNull?.phoneNumber.orEmpty()),
        val address: InputFieldState<String> = InputFieldState(currentUser.valueOrNull?.address.orEmpty()),
    ) {
        val saveDetailsError = when {
            fullName.isInvalid -> fullName.error?.message
            phoneNumber.isInvalid -> phoneNumber.error?.message
            address.isInvalid -> address.error?.message
            else -> null
        }

        val isSaveDetailsButtonEnabled = fullName.value.isNotBlank() &&
                phoneNumber.value.isNotBlank() &&
                address.value.isNotBlank()
    }

    sealed class Effect {
        data object NavigateBack : Effect()
    }
}
