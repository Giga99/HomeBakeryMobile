package com.pki.homebakery.features.dashboard.presentation

import com.pki.homebakery.features.dashboard.data.DashboardService
import com.pki.homebakery.features.dashboard.domain.Cake
import com.pki.homebakery.features.dashboard.presentation.DashboardViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import kotlinx.coroutines.delay
import org.koin.android.annotation.KoinViewModel
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
class DashboardViewModel(
    private val dashboardService: DashboardService,
) : BaseViewModel<State, Unit>(State()) {

    init {
        launchInViewModel {
            runCatching { delay(2.seconds); dashboardService.getAllCakes() }
                .onSuccess { cakes ->
                    updateState { state.copy(cakesStatus = cakes.asLoaded()) }
                }.onFailure {
                    updateState { state.copy(cakesStatus = it.asFailed()) }
                }
        }
    }

    data class State(
        val cakesStatus: UIState<List<Cake>> = UIState.Loading(),
    ) {
        val promotions =
            cakesStatus.valueOrNull?.filter { it.id == "2" || it.id == "3" || it.id == "4" }
        val allCakes = cakesStatus.valueOrNull
    }
}
