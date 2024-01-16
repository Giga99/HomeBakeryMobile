package com.pki.homebakery.features.cart.presentation

import com.pki.homebakery.features.cart.data.OrderService
import com.pki.homebakery.features.cart.domain.CartItem
import com.pki.homebakery.features.cart.presentation.CartViewModel.State
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import com.pki.homebakery.ui.viewmodel.asLoaded
import org.koin.android.annotation.KoinViewModel

private const val FETCH_CART_ITEMS_JOB = "FETCH_CART_ITEMS_JOB"

@KoinViewModel
class CartViewModel(
    private val orderService: OrderService,
) : BaseViewModel<State, Unit>(State()) {

    init {
        fetchCartItems()
    }

    fun refresh() {
        fetchCartItems()
    }

    private fun fetchCartItems() = launchUniqueIfNotRunning(FETCH_CART_ITEMS_JOB) {
        runCatching { orderService.getCart() }
            .onSuccess { updateState { state.copy(cartItems = it.asLoaded()) } }
            .onFailure { updateState { state.copy(cartItems = it.asFailed()) } }
    }

    data class State(
        val cartItems: UIState<List<CartItem>> = UIState.Loading(),
    )
}
