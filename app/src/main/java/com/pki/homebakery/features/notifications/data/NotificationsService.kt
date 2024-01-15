package com.pki.homebakery.features.notifications.data

import com.pki.homebakery.common.notifications
import com.pki.homebakery.features.notifications.domain.Notification
import org.koin.core.annotation.Factory

@Factory
class NotificationsService {

    fun getNotifications(): List<Notification> {
        return notifications
    }
}
