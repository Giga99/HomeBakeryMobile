package com.pki.homebakery.features.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.common.blackForestCake
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.HorizontalDivider
import com.pki.homebakery.ui.components.IconButton
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.components.bar.rememberElevateTopBar
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsStateAndEffects
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CakeDetailsScreen(cakeId: String) {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<CakeDetailsViewModel> { parametersOf(cakeId) }

    val state by viewModel.collectAsStateAndEffects {

    }

    CakeDetailsContent(
        state = state,
        onBackClick = appNavigator::navigateBack,
        onAddCommentClick = {}
    )
}

@Composable
private fun CakeDetailsContent(
    state: CakeDetailsViewModel.State,
    onBackClick: () -> Unit,
    onAddCommentClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val cake = state.cake
    Scaffold(
        topBar = {
            CakeDetailsTopBar(
                cakeTitle = cake.title,
                onBackClick = onBackClick,
                onAddCommentClick = onAddCommentClick,
                elevated = rememberElevateTopBar(scrollState).value,
            )
        },
        bottomBar = {

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(cake.icon),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Ingredients(
                        ingredients = cake.ingredients,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    )
                    Text(
                        text = cake.price,
                        style = AppTypography.bodyBold,
                    )
                }
                HorizontalDivider(Modifier.padding(vertical = 16.dp))
                Description(
                    description = cake.description,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Ingredients(
    ingredients: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Ingredients",
            style = AppTypography.bodyBold,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        ingredients.forEach { ingredient ->
            Text(
                text = "• $ingredient",
                style = AppTypography.note,
                modifier = Modifier.padding(bottom = 4.dp),
            )
        }
    }
}

@Composable
private fun Description(
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Description",
            style = AppTypography.bodyBold,
        )
        Text(
            text = description,
            style = AppTypography.note,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
        )
        HorizontalDivider()
    }
}

@Composable
private fun CakeDetailsTopBar(
    cakeTitle: String,
    elevated: Boolean,
    onBackClick: () -> Unit,
    onAddCommentClick: () -> Unit,
) {
    TopAppBar(
        title = cakeTitle,
        onBackPressed = onBackClick,
        topAppBarActions = {
            IconButton(onClick = onAddCommentClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = null,
                    tint = AppColors.darkGrey
                )
            }
        },
        elevated = elevated
    )
}

@Composable
@ScreenPreviews
private fun CakeDetailsPreview() {
    PreviewView {
        CakeDetailsContent(
            state = CakeDetailsViewModel.State(
                cake = blackForestCake,
            ),
            onBackClick = {},
            onAddCommentClick = {}
        )
    }
}
