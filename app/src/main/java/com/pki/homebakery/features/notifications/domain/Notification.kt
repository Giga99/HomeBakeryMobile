package com.pki.homebakery.features.notifications.domain

data class Notification(
    val orderId: String,
    val isOrderAccepted: Boolean,
)
