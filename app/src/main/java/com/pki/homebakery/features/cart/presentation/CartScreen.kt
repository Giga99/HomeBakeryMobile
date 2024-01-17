package com.pki.homebakery.features.cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pki.homebakery.R
import com.pki.homebakery.features.cart.presentation.CartViewModel.CartItemUIModel
import com.pki.homebakery.features.cart.presentation.CartViewModel.DialogContent
import com.pki.homebakery.ui.LifecycleObserver
import com.pki.homebakery.ui.components.Button
import com.pki.homebakery.ui.components.IconButton
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.bar.BottomAppBar
import com.pki.homebakery.ui.components.content.FullScreenContent
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.priceToPrettyString
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppShapes
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen() {
    val viewModel = koinViewModel<CartViewModel>()

    val state by viewModel.collectAsState()

    LifecycleObserver(
        onResumed = viewModel::refresh,
    )

    CartContent(
        state = state,
        onToggleItemSelected = viewModel::onToggleItemSelected,
        onIncrementClick = viewModel::onIncrementClick,
        onDecrementClick = viewModel::onDecrementClick,
        onOrderClick = viewModel::onOrder,
    )

    state.dialogContent?.let { dialogContent ->
        when (dialogContent) {
            is DialogContent.SuccessfullyOrdered -> {
                Dialog(onDismissRequest = viewModel::dismissDialog) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = AppColors.background, shape = AppShapes.extraLarge)
                            .padding(horizontal = 16.dp, vertical = 35.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "You successfully ordered items!",
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
private fun CartContent(
    state: CartViewModel.State,
    onToggleItemSelected: (CartItemUIModel) -> Unit,
    onIncrementClick: (CartItemUIModel) -> Unit,
    onDecrementClick: (CartItemUIModel) -> Unit,
    onOrderClick: () -> Unit,
) {
    val numOfItems =
        if (state.numberOfSelectedItems == 1) "1 item" else "${state.numberOfSelectedItems} items"
    Scaffold(
        bottomBar = {
            BottomAppBar {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estimated Total ($numOfItems)",
                            style = AppTypography.noteBold,
                        )
                        Text(
                            text = state.totalPrice.priceToPrettyString(),
                            style = AppTypography.noteBold,
                        )
                    }
                    Button(
                        onClick = onOrderClick,
                        enabled = state.numberOfSelectedItems > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "Order")
                    }
                }
            }
        }
    ) {
        FullScreenContent(state = state.cartItemsStatus) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                Text(
                    text = "My Cart",
                    style = AppTypography.h2Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 32.dp)
                ) {
                    items(state.cartItems) {
                        CartItem(
                            cartItemUIModel = it,
                            onToggleItemSelected = onToggleItemSelected,
                            onIncrementClick = onIncrementClick,
                            onDecrementClick = onDecrementClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItem(
    cartItemUIModel: CartItemUIModel,
    onToggleItemSelected: (CartItemUIModel) -> Unit,
    onIncrementClick: (CartItemUIModel) -> Unit,
    onDecrementClick: (CartItemUIModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val cake = cartItemUIModel.cartItem.cake
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { onToggleItemSelected(cartItemUIModel) }) {
            Icon(
                painter = painterResource(if (cartItemUIModel.isSelected) R.drawable.ic_selected else R.drawable.ic_not_selected),
                contentDescription = null,
                tint = AppColors.grey
            )
        }
        Image(
            painter = painterResource(cake.icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(AppShapes.extraSmall)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(text = cake.title)
            Text(
                text = cake.price.priceToPrettyString(),
                style = AppTypography.note,
                color = AppColors.darkGrey,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        CartItemAmountInput(
            currentAmount = cartItemUIModel.cartItem.amount,
            onIncrementClick = { onIncrementClick(cartItemUIModel) },
            onDecrementClick = { onDecrementClick(cartItemUIModel) },
        )
    }
}

@Composable
private fun CartItemAmountInput(
    currentAmount: Int,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onDecrementClick) {
            Icon(
                painter = painterResource(if (currentAmount == 1) R.drawable.ic_delete else R.drawable.ic_decrease_quantity),
                contentDescription = null,
                tint = AppColors.grey,
            )
        }
        Text(
            text = currentAmount.toString(),
            style = AppTypography.h3Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
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
@ScreenPreviews
private fun CartScreenPreviews() {
    PreviewView {
        CartContent(
            state = CartViewModel.State(),
            onToggleItemSelected = {},
            onIncrementClick = {},
            onDecrementClick = {},
            onOrderClick = {},
        )
    }
}
