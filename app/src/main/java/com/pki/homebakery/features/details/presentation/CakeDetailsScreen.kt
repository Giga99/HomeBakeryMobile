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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pki.homebakery.R
import com.pki.homebakery.common.blackForestCake
import com.pki.homebakery.features.addcomment.presentation.AddCommentDestination
import com.pki.homebakery.features.dashboard.domain.Comment
import com.pki.homebakery.features.details.presentation.CakeDetailsViewModel.DialogContent
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.LifecycleObserver
import com.pki.homebakery.ui.components.Button
import com.pki.homebakery.ui.components.HorizontalDivider
import com.pki.homebakery.ui.components.IconButton
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.BottomAppBar
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.components.bar.rememberElevateTopBar
import com.pki.homebakery.ui.components.content.FullScreenContent
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.secondsToPrettyString
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppShapes
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.asLoaded
import com.pki.homebakery.ui.viewmodel.collectAsStateAndEffects
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant

@Composable
fun CakeDetailsScreen(cakeId: String) {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<CakeDetailsViewModel> { parametersOf(cakeId) }

    val state by viewModel.collectAsStateAndEffects {
        when (it) {
            CakeDetailsViewModel.Effect.NavigateBack -> appNavigator.navigateBack()
        }
    }

    LifecycleObserver(onResumed = viewModel::refresh)

    CakeDetailsContent(
        state = state,
        onBackClick = appNavigator::navigateBack,
        onAddCommentClick = {
            appNavigator.navigateTo(
                AddCommentDestination(
                    state.cake?.id.orEmpty(),
                    state.cake?.title.orEmpty()
                )
            )
        },
        onIncrementClick = viewModel::onIncreaseAmountClick,
        onDecrementClick = viewModel::onDecreaseAmountClick,
        onAddToCartClick = viewModel::addToCart,
    )

    state.dialogContent?.let { dialogContent ->
        when (dialogContent) {
            is DialogContent.AddedSuccessfullyToCart -> {
                Dialog(onDismissRequest = viewModel::dismissDialog) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = AppColors.background, shape = AppShapes.extraLarge)
                            .padding(horizontal = 16.dp, vertical = 35.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "You successfully added items to cart!",
                            style = AppTypography.body,
                            modifier = Modifier.padding(bottom = 24.dp),
                        )
                        Button(onClick = viewModel::dismissDialog) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CakeDetailsContent(
    state: CakeDetailsViewModel.State,
    onBackClick: () -> Unit,
    onAddCommentClick: () -> Unit,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    onAddToCartClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    FullScreenContent(state = state.cakeStatus) { cake ->
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
                CakeDetailsBottomBar(
                    currentAmount = state.currentAmount,
                    currentPrice = state.currentPrice,
                    onIncrementClick = onIncrementClick,
                    onDecrementClick = onDecrementClick,
                    onAddToCartClick = onAddToCartClick,
                    modifier = Modifier.fillMaxWidth(),
                )
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
                            text = "${cake.price} €",
                            style = AppTypography.bodyBold,
                        )
                    }
                    HorizontalDivider(Modifier.padding(vertical = 16.dp))
                    Description(
                        description = cake.description,
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider(Modifier.padding(vertical = 16.dp))
                    Comments(
                        comments = cake.comments,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
private fun Comments(
    comments: List<Comment>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = "Comments",
            style = AppTypography.bodyBold,
            modifier = Modifier.padding(bottom = 4.dp),
        )
        comments.forEach { comment ->
            CommentItem(
                comment = comment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
            )
        }
    }
}

@Composable
private fun CommentItem(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = comment.getTitle(),
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = comment.text,
            style = AppTypography.note,
            modifier = Modifier.padding(top = 4.dp),
        )
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
private fun CakeDetailsBottomBar(
    currentAmount: Int,
    currentPrice: Int,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CakeAmountInput(
                currentAmount = currentAmount,
                onIncrementClick = onIncrementClick,
                onDecrementClick = onDecrementClick,
                modifier = Modifier.padding(end = 24.dp),
            )
            Button(
                onClick = onAddToCartClick,
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "Add for $currentPrice €")
            }
        }
    }
}

@Composable
private fun CakeAmountInput(
    currentAmount: Int,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onDecrementClick,
            enabled = currentAmount > 1,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_decrease_quantity),
                contentDescription = null,
                tint = if (currentAmount > 1) AppColors.action else AppColors.grey,
            )
        }
        Text(
            text = currentAmount.toString(),
            style = AppTypography.h3Bold,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        IconButton(onClick = onIncrementClick) {
            Icon(
                painter = painterResource(R.drawable.ic_increase_quantity),
                contentDescription = null,
                tint = AppColors.action,
            )
        }
    }
}

@Composable
private fun Comment.getTitle() = buildAnnotatedString {
    val differenceInSeconds = Instant.now().epochSecond - date.epochSecond
    withStyle(AppTypography.noteBold.toSpanStyle()) {
        append(username)
    }
    withStyle(AppTypography.note.toSpanStyle().copy(color = AppColors.darkGrey)) {
        append("  •  ")
        append(differenceInSeconds.secondsToPrettyString())
    }
}

@Composable
@ScreenPreviews
private fun CakeDetailsPreview() {
    PreviewView {
        CakeDetailsContent(
            state = CakeDetailsViewModel.State(
                cakeStatus = blackForestCake.asLoaded(),
            ),
            onBackClick = {},
            onAddCommentClick = {},
            onIncrementClick = { /*TODO*/ },
            onDecrementClick = { /*TODO*/ },
            onAddToCartClick = { /*TODO*/ },
        )
    }
}
