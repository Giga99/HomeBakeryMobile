package com.pki.homebakery.features.notifications.data

import com.pki.homebakery.common.notifications
import com.pki.homebakery.features.notifications.domain.Notification
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@Factory
class NotificationsService {

    private val notificationsCache = notifications.toMutableList()

    suspend fun getNotifications(): List<Notification> {
        delay(1.seconds)
        val orderId = Random.nextInt(10000, 99999).toString()
        val isOrderAccepted = Random.nextBoolean()
        notificationsCache.add(
            Notification(
                orderId = orderId,
                isOrderAccepted = isOrderAccepted,
            )
        )
        return notificationsCache
    }
}
