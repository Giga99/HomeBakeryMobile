package com.pki.homebakery.features.homebakeryinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography

@Composable
fun HomeBakeryInfoScreen() {
    val appNavigator = LocalAppNavigator.current

    HomeBakeryInfoContent(onBackPressed = appNavigator::navigateBack)
}

@Composable
private fun HomeBakeryInfoContent(onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Home Bakery Info",
                onBackPressed = onBackPressed
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
            .padding(16.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            HomeBakeryInfoItem(
                label = "Name",
                value = "Home Bakery",
                modifier = Modifier.padding(top = 80.dp)
            )
            HomeBakeryInfoItem(
                label = "Address",
                value = "Street 456",
                modifier = Modifier.padding(top = 16.dp)
            )
            HomeBakeryInfoItem(
                label = "Phone Number",
                value = "+381621234567",
                modifier = Modifier.padding(top = 16.dp)
            )
            HomeBakeryInfoItem(
                label = "Description",
                value = "Indulge in a world where the aroma of freshly baked treats fills the air and every bite is a piece of heaven. Home Bakery is your go-to app for ordering mouth-watering cakes, cupcakes, and pastries right from the comfort of your home.",
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}

@Composable
private fun HomeBakeryInfoItem(
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
private fun HomeBakeryInfoPreview() {
    PreviewView {
        HomeBakeryInfoContent(onBackPressed = {})
    }
}
