package com.pki.homebakery.features.cart.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.pki.homebakery.ui.LifecycleObserver
import com.pki.homebakery.ui.viewmodel.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen() {
    val viewModel = koinViewModel<CartViewModel>()

    val state by viewModel.collectAsState()

    LifecycleObserver(
        onResumed = viewModel::refresh,
    )

    Text(text = "CART: ${state.cartItems.valueOrNull?.size}")
    state.cartItems.valueOrNull?.forEach {
        Text(text = "CART ITEM: ${it.cake.title} and amount: ${it.amount}")
    }
}
