package com.pki.homebakery.features.register.domain

import com.pki.homebakery.features.register.data.RegisterRequest

data class RegisterInput(
    val username: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val address: String,
)

fun RegisterInput.toRequest(): RegisterRequest =
    RegisterRequest(
        username = username,
        password = password,
        fullName = fullName,
        phoneNumber = phoneNumber,
        address = address,
    )
