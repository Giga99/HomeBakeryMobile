package com.pki.homebakery.features.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.features.editpassword.EditPasswordDestination
import com.pki.homebakery.features.editpersonaldetails.EditPersonalDetailsDestination
import com.pki.homebakery.features.login.presentation.LoginDestination
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Button
import com.pki.homebakery.ui.components.ButtonDefaults
import com.pki.homebakery.ui.components.ModalBottomSheet
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.BottomAppBar
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsStateAndEffects
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen() {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<SettingsViewModel>()

    val state by viewModel.collectAsStateAndEffects { effect ->
        when (effect) {
            is SettingsViewModel.Effect.NavigateToLogin -> appNavigator.navigateTo(LoginDestination())
        }
    }

    SettingsContent(
        onBackClick = appNavigator::navigateBack,
        onEditPersonalDetailsClick = {
            appNavigator.navigateTo(EditPersonalDetailsDestination())
        },
        onEditPasswordClick = {
            appNavigator.navigateTo(EditPasswordDestination())
        },
        onLogOutClick = viewModel::logout,
    )

    state.bottomSheetContent?.let {
        ModalBottomSheet(onDismissRequest = viewModel::dismissBottomSheet) {
            SettingsBottomSheet(
                bottomSheetContent = it,
                onLogOutClick = viewModel::confirmLogout,
                onCancelClick = viewModel::dismissBottomSheet,
            )
        }
    }
}

@Composable
private fun SettingsContent(
    onBackClick: () -> Unit,
    onEditPersonalDetailsClick: () -> Unit,
    onEditPasswordClick: () -> Unit,
    onLogOutClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Settings",
                onBackPressed = onBackClick
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = onLogOutClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.error,
                        contentColor = AppColors.white
                    )
                ) {
                    Text(text = "Log Out")
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
            .padding(16.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            SettingsItem(title = "Edit Personal Details", onClick = onEditPersonalDetailsClick)
            SettingsItem(title = "Edit Password", onClick = onEditPasswordClick)
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.background)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = null,
            tint = AppColors.text
        )
    }
}

@Composable
private fun SettingsBottomSheet(
    bottomSheetContent: SettingsViewModel.BottomSheetContent,
    onLogOutClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    when (bottomSheetContent) {
        SettingsViewModel.BottomSheetContent.LogoutBottomSheetContent -> {
            LogoutSheet(
                onLogOutClick = onLogOutClick,
                onCancelClick = onCancelClick,
            )
        }
    }
}

@Composable
private fun LogoutSheet(
    onLogOutClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Are you sure?",
            style = AppTypography.h2Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onLogOutClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.error,
                contentColor = AppColors.white
            )
        ) {
            Text(text = "Log Out")
        }
        Button(
            onClick = onCancelClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.darkGrey,
                contentColor = AppColors.white
            )
        ) {
            Text(text = "Cancel")
        }
    }
}

@Composable
@ScreenPreviews
private fun SettingsPreview() {
    PreviewView {
        SettingsContent(
            onBackClick = {},
            onEditPersonalDetailsClick = {},
            onEditPasswordClick = {},
            onLogOutClick = {},
        )
    }
}
