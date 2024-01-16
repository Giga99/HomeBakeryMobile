package com.pki.homebakery.features.cart.data

import com.pki.homebakery.features.cart.domain.CartItem
import com.pki.homebakery.features.dashboard.domain.Cake
import org.koin.core.annotation.Single

@Single
class OrderService {
    private val currentCart = mutableMapOf<String, CartItem>()

    suspend fun addToCart(amount: Int, cake: Cake) {
        val existingCartItem = currentCart[cake.id]
        if (existingCartItem != null) {
            currentCart[cake.id] = existingCartItem.copy(amount = existingCartItem.amount + amount)
        } else {
            currentCart[cake.id] = CartItem(amount, cake)
        }
    }

    suspend fun getCart(): List<CartItem> {
        return currentCart.values.toList()
    }
}
