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
private const val ORDER_JOB = "ORDER_JOB"

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

    fun onToggleItemSelected(cartItemUIModel: CartItemUIModel) {
        updateState {
            val newCartItems = state.cartItems.map {
                if (it == cartItemUIModel) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it
                }
            }
            state.copy(cartItemsStatus = newCartItems.asLoaded())
        }
    }

    fun onIncrementClick(cartItemUIModel: CartItemUIModel) {
        updateState {
            val newCartItems = state.cartItems.map {
                if (it == cartItemUIModel) {
                    it.copy(cartItem = it.cartItem.copy(amount = it.cartItem.amount + 1))
                } else {
                    it
                }
            }
            state.copy(cartItemsStatus = newCartItems.asLoaded())
        }
    }

    fun onDecrementClick(cartItemUIModel: CartItemUIModel) {
        updateState {
            val newCartItems = if (cartItemUIModel.cartItem.amount == 1) {
                state.cartItems.filterNot { it == cartItemUIModel }
            } else {
                state.cartItems.map {
                    if (it == cartItemUIModel) {
                        it.copy(cartItem = it.cartItem.copy(amount = it.cartItem.amount - 1))
                    } else {
                        it
                    }
                }
            }
            state.copy(cartItemsStatus = newCartItems.asLoaded())
        }
    }

    fun onOrder() = launchUniqueIfNotRunning(ORDER_JOB) {
        runCatching { orderService.orderCart() }
            .onSuccess {
                refresh()
                showDialog(DialogContent.SuccessfullyOrdered)
            }.onFailure {
                updateState { state.copy(cartItemsStatus = it.asFailed()) }
            }
    }

    fun dismissDialog() {
        updateState { state.copy(dialogContent = null) }
    }

    private fun showDialog(dialogContent: DialogContent) {
        updateState { state.copy(dialogContent = dialogContent) }
    }

    private fun fetchCartItems() = launchUniqueIfNotRunning(FETCH_CART_ITEMS_JOB) {
        runCatching { orderService.getCart() }
            .onSuccess {
                updateState {
                    state.copy(cartItemsStatus = it.map { it.toUIModel() }.asLoaded())
                }
            }
            .onFailure { updateState { state.copy(cartItemsStatus = it.asFailed()) } }
    }

    private fun CartItem.toUIModel() = CartItemUIModel(
        cartItem = this,
        isSelected = true,
    )

    data class State(
        val cartItemsStatus: UIState<List<CartItemUIModel>> = UIState.Loading(),
        val dialogContent: DialogContent? = null,
    ) {
        val cartItems = cartItemsStatus.valueOrNull ?: emptyList()
        val numberOfSelectedItems =
            cartItems.filter { it.isSelected }.sumOf { it.cartItem.amount }
        val totalPrice =
            cartItems.filter { it.isSelected }
                .sumOf { it.cartItem.amount * it.cartItem.cake.price }
    }

    sealed class DialogContent {
        data object SuccessfullyOrdered : DialogContent()
    }

    data class CartItemUIModel(
        val cartItem: CartItem,
        val isSelected: Boolean,
    )
}
