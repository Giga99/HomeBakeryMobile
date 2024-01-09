package com.pki.homebakery.features.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.features.settings.presentation.SettingsDestination
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.LifecycleObserver
import com.pki.homebakery.ui.components.IconButton
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.components.content.FullScreenContent
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen() {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<ProfileViewModel>()

    val state by viewModel.collectAsState()

    LifecycleObserver(onResumed = viewModel::refresh)

    ProfileContent(
        state = state,
        onSettingsClick = { appNavigator.navigateTo(SettingsDestination()) }
    )
}

@Composable
private fun ProfileContent(
    state: ProfileViewModel.State,
    onSettingsClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                topAppBarActions = {
                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings),
                            contentDescription = null,
                            tint = AppColors.darkGrey,
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
    ) {
        FullScreenContent(state = state.profileInfo) { profileInfo ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "My Profile",
                    style = AppTypography.h2Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                ProfileInfoItem(
                    label = "Full Name",
                    value = profileInfo.fullName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 72.dp),
                )
                ProfileInfoItem(
                    label = "Username",
                    value = profileInfo.username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                ProfileInfoItem(
                    label = "Phone Number",
                    value = profileInfo.phoneNumber,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
                ProfileInfoItem(
                    label = "Address",
                    value = profileInfo.address,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = AppTypography.noteBold,
            color = AppColors.grey,
        )
        Text(
            text = value,
            style = AppTypography.body,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
@ScreenPreviews
private fun ProfilePreview() {
    PreviewView {
        ProfileContent(
            state = ProfileViewModel.State(),
            onSettingsClick = { },
        )
    }
}
