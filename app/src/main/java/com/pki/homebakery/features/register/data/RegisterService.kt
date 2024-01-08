package com.pki.homebakery.features.register.data

import com.pki.homebakery.common.loginData
import com.pki.homebakery.features.login.data.LoginRequest
import com.pki.homebakery.features.register.domain.RegisterInput
import com.pki.homebakery.features.register.domain.toRequest
import org.koin.core.annotation.Factory

@Factory
class RegisterService {

    suspend fun register(input: RegisterInput) {
        val request = input.toRequest()
        loginData.add(LoginRequest(request.username, request.password))
    }
}
