package com.pki.homebakery.features.profile.presentation

import com.pki.homebakery.features.profile.data.ProfileService
import com.pki.homebakery.features.profile.domain.ProfileInfo
import com.pki.homebakery.features.profile.presentation.ProfileViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val profileService: ProfileService,
) : BaseViewModel<State, Unit>(State()) {

    init {
        refresh()
    }

    fun refresh() = launchInViewModel {
        runCatching { profileService.getProfileInfo() }
            .onSuccess { info ->
                updateState { state.copy(profileInfo = info.asLoaded()) }
            }.onFailure {
                updateState { state.copy(profileInfo = it.asFailed()) }
            }
    }

    data class State(
        val profileInfo: UIState<ProfileInfo> = UIState.Loading()
    )
}
