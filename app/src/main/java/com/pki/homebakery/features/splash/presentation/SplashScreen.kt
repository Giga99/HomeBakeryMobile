package com.pki.homebakery.features.splash.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.pki.homebakery.R
import com.pki.homebakery.features.home.presentation.HomeDestination
import com.pki.homebakery.features.login.presentation.LoginDestination
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.viewmodel.OnEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen() {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<SplashViewModel>()

    viewModel.OnEffect {
        when (it) {
            SplashViewModel.Effect.NavigateToHome -> appNavigator.navigateTo(HomeDestination())
            SplashViewModel.Effect.NavigateToLogin -> appNavigator.navigateTo(LoginDestination())
        }
    }

    SplashContent()
}

@Composable
private fun SplashContent() {
    Scaffold(
        containerColor = AppColors.action,
        statusBarColor = AppColors.action,
        navigationBarColor = AppColors.action,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
@ScreenPreviews
private fun SplashScreenPreview() {
    PreviewView {
        SplashContent()
    }
}
