package com.pki.homebakery.features.splash.presentation

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.users
import com.pki.homebakery.features.splash.presentation.SplashViewModel.Effect
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
class SplashViewModel(
    private val prefsService: PrefsService,
) : BaseViewModel<Unit, Effect>(Unit) {

    init {
        launchInViewModel {
            runCatching {
                delay(2.seconds)
                prefsService.getCurrentUser()
            }.onSuccess { username ->
                if (username.isNullOrBlank() || users.none { it.username == username }) {
                    effect(Effect.NavigateToLogin)
                } else {
                    effect(Effect.NavigateToHome)
                }
            }
        }
    }

    sealed class Effect {
        data object NavigateToLogin : Effect()

        data object NavigateToHome : Effect()
    }
}
