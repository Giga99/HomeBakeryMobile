package com.pki.homebakery.features.settings.presentation

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.features.settings.presentation.SettingsViewModel.Effect
import com.pki.homebakery.features.settings.presentation.SettingsViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import org.koin.android.annotation.KoinViewModel

private const val LOGOUT_JOB = "LOGOUT_JOB"

@KoinViewModel
class SettingsViewModel(
    private val prefsService: PrefsService,
) : BaseViewModel<State, Effect>(State()) {

    fun logout() {
        updateState { state.copy(bottomSheetContent = BottomSheetContent.LogoutBottomSheetContent) }
    }

    fun dismissBottomSheet() {
        updateState { state.copy(bottomSheetContent = null) }
    }

    fun confirmLogout() = launchUniqueIfNotRunning(LOGOUT_JOB) {
        dismissBottomSheet()
        runCatching { prefsService.removeCurrentUser() }
            .onSuccess { effect(Effect.NavigateToLogin) }
    }

    data class State(
        val bottomSheetContent: BottomSheetContent? = null,
    )

    sealed class Effect {
        data object NavigateToLogin : Effect()
    }

    sealed class BottomSheetContent {
        data object LogoutBottomSheetContent : BottomSheetContent()
    }
}
