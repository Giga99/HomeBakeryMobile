package com.pki.homebakery.features.dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.features.dashboard.domain.Cake
import com.pki.homebakery.features.homebakeryinfo.presentation.HomeBakeryInfoDestination
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Card
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
fun DashboardScreen() {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<DashboardViewModel>()

    val state by viewModel.collectAsState()

    DashboardContent(
        state = state,
        onCakeClick = { cake ->

        },
        onInfoClick = {
            appNavigator.navigateTo(HomeBakeryInfoDestination())
        },
    )
}

@Composable
private fun DashboardContent(
    state: DashboardViewModel.State,
    onCakeClick: (Cake) -> Unit,
    onInfoClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                topAppBarActions = {
                    IconButton(
                        onClick = onInfoClick,
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_info),
                            contentDescription = null,
                            tint = AppColors.darkGrey,
                        )
                    }
                },
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
            .padding(horizontal = 16.dp),
    ) {
        FullScreenContent(state = state.cakesStatus) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (state.promotions?.isNotEmpty() == true) {
                    item {
                        Text(
                            text = "Promotions",
                            style = AppTypography.h2Bold,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    }
                    item {
                        Promotions(
                            promotions = state.promotions,
                            onCakeClick = onCakeClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        )
                    }
                }
                item {
                    Text(
                        text = "Menu",
                        style = AppTypography.h2Bold,
                        modifier = Modifier.padding(top = 34.dp)
                    )
                }
                items(state.allCakes ?: emptyList()) {
                    CakeItem(
                        cake = it,
                        onClick = onCakeClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun Promotions(
    promotions: List<Cake>,
    onCakeClick: (Cake) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .height(intrinsicSize = IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        promotions.forEach {
            PromotionItem(
                cake = it,
                onClick = onCakeClick,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 16.dp),
            )
        }
    }
}

@Composable
private fun PromotionItem(
    cake: Cake,
    onClick: (Cake) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(cake) },
        modifier = modifier,
        elevation = 2.dp,
    ) {
        Column(Modifier.width(180.dp)) {
            Image(
                painter = painterResource(cake.icon),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = cake.title,
                modifier = Modifier.padding(top = 9.dp, start = 7.dp, end = 7.dp),
            )
            Text(
                text = cake.subtitle,
                style = AppTypography.note,
                color = AppColors.darkGrey,
                modifier = Modifier.padding(top = 4.dp, start = 7.dp, end = 7.dp, bottom = 15.dp),
            )
        }
    }
}

@Composable
private fun CakeItem(
    cake: Cake,
    onClick: (Cake) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable { onClick(cake) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(Modifier.wrapContentSize()) {
            Image(
                painter = painterResource(cake.icon),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(102.dp, 114.dp),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
        ) {
            Text(text = cake.title)
            Text(
                text = cake.subtitle,
                style = AppTypography.note,
                color = AppColors.darkGrey,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}

@Composable
@ScreenPreviews
private fun DashboardPreview() {
    PreviewView {
        DashboardContent(
            state = DashboardViewModel.State(),
            onCakeClick = {},
            onInfoClick = {},
        )
    }
}
