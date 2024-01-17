package com.pki.homebakery.features.addcomment.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.navigation.LocalAppNavigator
import com.pki.homebakery.ui.components.Button
import com.pki.homebakery.ui.components.Scaffold
import com.pki.homebakery.ui.components.TextField
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.components.content.FullScreenContent
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.collectAsStateAndEffects
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AddCommentScreen(cakeId: String, cakeTitle: String) {
    val appNavigator = LocalAppNavigator.current

    val viewModel = koinViewModel<AddCommentViewModel> { parametersOf(cakeId, cakeTitle) }

    val state by viewModel.collectAsStateAndEffects {
        when (it) {
            AddCommentViewModel.Effect.FinishAdding -> appNavigator.navigateBack()
        }
    }

    AddCommentContent(
        state = state,
        onCommentTextChange = viewModel::onCommentTextChange,
        onAddCommentClick = viewModel::onAddCommentClick,
        onBackClick = appNavigator::navigateBack,
    )
}

@Composable
private fun AddCommentContent(
    state: AddCommentViewModel.State,
    onCommentTextChange: (String) -> Unit,
    onAddCommentClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = state.cakeTitle,
                onBackPressed = onBackClick,
            )
        }
    ) {
        FullScreenContent(state = state.addCommentStatus) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Add Comment",
                    style = AppTypography.h2Bold,
                    modifier = Modifier.padding(top = 8.dp),
                )
                TextField(
                    value = state.comment.value,
                    onValueChange = onCommentTextChange,
                    placeholder = { Text(text = "Write a comment...", style = AppTypography.body) },
                    leadingIcon = R.drawable.ic_comment,
                    isError = state.comment.isInvalid,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onAddCommentClick() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onAddCommentClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add Comment")
                }
            }
        }
    }
}

@Composable
@ScreenPreviews
private fun AddCommentContentPreview() {
    PreviewView {
        AddCommentContent(
            state = AddCommentViewModel.State(
                cakeId = "1",
                cakeTitle = "Chocolate Cake",
            ),
            onCommentTextChange = {},
            onAddCommentClick = {},
            onBackClick = {},
        )
    }
}
