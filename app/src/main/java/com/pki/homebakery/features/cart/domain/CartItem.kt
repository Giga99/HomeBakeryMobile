package com.pki.homebakery.features.cart.domain

import com.pki.homebakery.features.dashboard.domain.Cake

data class CartItem(
    val amount: Int,
    val cake: Cake,
)
