package com.pki.homebakery.features.login.data

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.users
import org.koin.core.annotation.Factory

@Factory
class LoginService(
    private val prefsService: PrefsService,
) {

    suspend fun login(username: String, password: String) {
        val request = LoginRequest(username, password)
        val areCredentialsFine =
            users.any { it.username == request.username && it.password == request.password }
        if (!areCredentialsFine) throw RuntimeException("Invalid credentials")
        prefsService.setCurrentUser(username)
    }
}
