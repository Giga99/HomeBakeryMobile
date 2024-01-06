package com.pki.homebakery.ui

data class InputFieldState<T>(
    val value: T,
    val error: InputFieldError? = null
) {
    val isValid = error == null
    val isInvalid = error != null

    fun withValue(value: T) = copy(value = value, error = null)

    fun withError(error: InputFieldError?) = copy(error = error)
}

sealed class InputFieldError(
    override val message: String?
) : RuntimeException(message) {
    data object InvalidUsernameFormat : InputFieldError("Invalid username format")

    data object InvalidPasswordFormat : InputFieldError("Invalid password format")
}
