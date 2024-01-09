package com.pki.homebakery.features.profile.data

import com.pki.homebakery.common.PrefsService
import com.pki.homebakery.common.users
import com.pki.homebakery.features.profile.domain.ProfileInfo
import org.koin.core.annotation.Factory

@Factory
class ProfileService(
    private val prefsService: PrefsService
) {

    suspend fun getProfileInfo(): ProfileInfo {
        val currentUser = prefsService.getCurrentUser()
        return users.firstOrNull { it.username == currentUser } ?: error("User not found")
    }
}
