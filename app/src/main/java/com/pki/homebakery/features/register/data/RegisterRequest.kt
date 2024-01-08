package com.pki.homebakery.features.register.data

data class RegisterRequest(
    val username: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val address: String,
)
