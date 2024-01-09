package com.pki.homebakery.features.home.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.features.cart.CartScreen
import com.pki.homebakery.features.dashboard.DashboardScreen
import com.pki.homebakery.features.home.presentation.HomeViewModel.BottomNavigationItem
import com.pki.homebakery.features.notifications.NotificationsScreen
import com.pki.homebakery.features.profile.presentation.ProfileScreen
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.BottomAppBar
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.viewmodel.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val context = LocalContext.current

    val viewModel = koinViewModel<HomeViewModel>()

    val state by viewModel.collectAsState()

    BackHandler { (context as? Activity)?.finish() }

    HomeContent(
        state = state,
        onBottomNavigationItemClick = viewModel::changeSelectedTab,
    )
}

@Composable
private fun HomeContent(
    state: HomeViewModel.State,
    onBottomNavigationItemClick: (BottomNavigationItem) -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                selectedItem = state.selectedTab,
                onItemClick = onBottomNavigationItemClick
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            when (state.selectedTab) {
                BottomNavigationItem.Dashboard -> DashboardScreen()
                BottomNavigationItem.Notification -> NotificationsScreen()
                BottomNavigationItem.Cart -> CartScreen()
                BottomNavigationItem.Profile -> ProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomNavigation(
    selectedItem: BottomNavigationItem,
    onItemClick: (BottomNavigationItem) -> Unit,
) {
    BottomAppBar(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomNavigationItem.entries.forEach { item ->
                BottomNavigationItem(
                    icon = item.getIcon(),
                    isSelected = item == selectedItem,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onItemClick(item) },
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationItem(
    @DrawableRes icon: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isSelected) AppColors.action else AppColors.grey,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 16.dp),
        )
    }
}

@Composable
private fun BottomNavigationItem.getIcon() =
    when (this) {
        BottomNavigationItem.Dashboard -> R.drawable.ic_home
        BottomNavigationItem.Notification -> R.drawable.ic_notifications
        BottomNavigationItem.Cart -> R.drawable.ic_cart
        BottomNavigationItem.Profile -> R.drawable.ic_profile
    }

@Composable
@ScreenPreviews
private fun HomePreview() {
    PreviewView {
        HomeContent(
            state = HomeViewModel.State(),
            onBottomNavigationItemClick = {}
        )
    }
}
