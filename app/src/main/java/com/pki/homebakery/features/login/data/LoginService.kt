package com.pki.homebakery.features.login.data

import com.pki.homebakery.common.loginData
import org.koin.core.annotation.Factory

@Factory
class LoginService {

    suspend fun login(username: String, password: String) {
        val request = LoginRequest(username, password)
        val areCredentialsFine = loginData.contains(request)
        if (!areCredentialsFine) throw RuntimeException("Invalid credentials")
    }
}
