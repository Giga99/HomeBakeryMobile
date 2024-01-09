package com.pki.homebakery.features.register.domain

import com.pki.homebakery.features.profile.domain.ProfileInfo

data class RegisterInput(
    val username: String,
    val password: String,
    val fullName: String,
    val phoneNumber: String,
    val address: String,
)

fun RegisterInput.toProfileInfo(): ProfileInfo =
    ProfileInfo(
        username = username,
        password = password,
        fullName = fullName,
        phoneNumber = phoneNumber,
        address = address,
    )
