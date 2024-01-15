package com.pki.homebakery.features.notifications.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pki.homebakery.features.notifications.domain.Notification
import com.pki.homebakery.ui.components.HorizontalDivider
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.content.FullScreenContent
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationsScreen() {
    val viewModel = koinViewModel<NotificationsViewModel>()

    val state by viewModel.collectAsState()

    NotificationsContent(state)
}

@Composable
private fun NotificationsContent(
    state: NotificationsViewModel.State,
) {
    Scaffold {
        FullScreenContent(state = state.notificationsStatus) { notifications ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "My Notifications",
                    style = AppTypography.h2Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp),
                    textAlign = TextAlign.Center,
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                ) {
                    items(notifications) {
                        NotificationItem(
                            notification = it,
                            modifier = Modifier.padding(bottom = 16.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: Notification,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = notification.getNotificationText(),
            style = AppTypography.body,
            color = notification.getNotificationTextColor(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        HorizontalDivider()
    }
}

private fun Notification.getNotificationText(): String =
    "Your order #${orderId} is ${if (isOrderAccepted) "accepted" else "declined"}."

@Composable
private fun Notification.getNotificationTextColor(): Color =
    if (isOrderAccepted) AppColors.text else AppColors.error.copy(alpha = 0.5f)

@Composable
@ScreenPreviews
private fun NotificationsPreview() {
    PreviewView {
        NotificationsContent(NotificationsViewModel.State())
    }
}
