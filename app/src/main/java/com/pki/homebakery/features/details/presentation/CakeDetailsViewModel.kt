package com.pki.homebakery.features.details.presentation

import com.pki.homebakery.features.cart.data.OrderService
import com.pki.homebakery.features.dashboard.domain.Cake
import com.pki.homebakery.features.details.data.CakeDetailsService
import com.pki.homebakery.features.details.presentation.CakeDetailsViewModel.Effect
import com.pki.homebakery.features.details.presentation.CakeDetailsViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import com.pki.homebakery.ui.viewmodel.asLoading
import org.koin.android.annotation.KoinViewModel

private const val FETCH_CAKE_DETAILS_JOB = "FETCH_CAKE_DETAILS_JOB"
private const val ADD_TO_CART_JOB = "ADD_TO_CART_JOB"

@KoinViewModel
class CakeDetailsViewModel(
    private val cakeId: String,
    private val cakeDetailsService: CakeDetailsService,
    private val orderService: OrderService,
) : BaseViewModel<State, Effect>(State()) {

    init {
        fetchCakeDetails()
    }

    fun onIncreaseAmountClick() {
        updateState { state.copy(currentAmount = state.currentAmount + 1) }
    }

    fun onDecreaseAmountClick() {
        updateState { state.copy(currentAmount = state.currentAmount - 1) }
    }

    fun addToCart() = launchUniqueIfNotRunning(ADD_TO_CART_JOB) {
        val cake = state.cake ?: error("Cake is null")
        val amount = state.currentAmount
        updateState { state.copy(cakeStatus = state.cakeStatus.asLoading()) }
        runCatching {
            orderService.addToCart(amount, cake)
        }.onSuccess {
            updateState { state.copy(cakeStatus = cake.asLoaded()) }
            showDialog(DialogContent.AddedSuccessfullyToCart)
        }.onFailure {
            updateState { state.copy(cakeStatus = it.asFailed()) }
        }
    }

    fun dismissDialog() {
        updateState { state.copy(dialogContent = null) }
    }

    fun refresh() {
        fetchCakeDetails()
    }

    private fun showDialog(dialogContent: DialogContent) {
        updateState { state.copy(dialogContent = dialogContent) }
    }

    private fun fetchCakeDetails() = launchUniqueIfNotRunning(FETCH_CAKE_DETAILS_JOB) {
        runCatching { cakeDetailsService.getCakeDetails(cakeId) }
            .onSuccess { updateState { state.copy(cakeStatus = it.asLoaded()) } }
            .onFailure { updateState { state.copy(cakeStatus = it.asFailed()) } }
    }

    data class State(
        val cakeStatus: UIState<Cake> = UIState.Loading(),
        val currentAmount: Int = 1,
        val dialogContent: DialogContent? = null,
    ) {
        val cake = cakeStatus.valueOrNull
        val currentPrice = (cake?.price ?: 0) * currentAmount
    }

    sealed class Effect {
        data object NavigateBack : Effect()
    }

    sealed class DialogContent {
        data object AddedSuccessfullyToCart : DialogContent()
    }
}
