package com.pki.homebakery.features.notifications.presentation

import com.pki.homebakery.features.notifications.data.NotificationsService
import com.pki.homebakery.features.notifications.domain.Notification
import com.pki.homebakery.features.notifications.presentation.NotificationsViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import org.koin.android.annotation.KoinViewModel

private const val GET_NOTIFICATIONS_JOB = "GET_NOTIFICATIONS_JOB"

@KoinViewModel
class NotificationsViewModel(
    private val notificationsService: NotificationsService,
) : BaseViewModel<State, Unit>(State()) {

    init {
        refresh()
    }

    fun refresh() = launchUniqueIfNotRunning(GET_NOTIFICATIONS_JOB) {
        updateState { state.copy(notificationsStatus = UIState.Loading()) }
        runCatching { notificationsService.getNotifications() }
            .onSuccess { updateState { state.copy(notificationsStatus = it.asLoaded()) } }
            .onFailure { updateState { state.copy(notificationsStatus = it.asFailed()) } }
    }

    data class State(
        val notificationsStatus: UIState<List<Notification>> = UIState.Loading()
    )
}
