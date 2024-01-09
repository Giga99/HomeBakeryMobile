package com.pki.homebakery.features.register.data

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.users
import com.pki.homebakery.features.register.domain.RegisterInput
import com.pki.homebakery.features.register.domain.toProfileInfo
import org.koin.core.annotation.Factory

@Factory
class RegisterService(
    private val prefsService: PrefsService
) {

    suspend fun register(input: RegisterInput) {
        val profileInfo = input.toProfileInfo()
        users.add(profileInfo)
        prefsService.setCurrentUser(input.username)
    }
}
