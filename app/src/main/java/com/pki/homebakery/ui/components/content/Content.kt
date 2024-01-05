package com.pki.homebakery.ui.components.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pki.homebakery.ui.viewmodel.FailureDisplayType
import com.pki.homebakery.ui.viewmodel.UIState

/**
 * Shows all states. The only exception is when error is dialog and content is loaded, then content is shown.
 * Note that this component does not need to be full screen. You can have multiple [Content] components as a single screen.
 */
@Composable
fun <T> Content(
    state: UIState<T>,
    modifier: Modifier = Modifier,
    loading: @Composable BoxScope.() -> Unit = { DefaultContentLoading() },
    failed: @Composable BoxScope.(UIState.Failed<T>) -> Unit = { DefaultContentFailure(it) },
    content: @Composable (T) -> Unit,
) {
    val loadedValue = state.loadedValue

    Box(modifier) {
        if (loadedValue != null && (state !is UIState.Failed || state.displayType != FailureDisplayType.FullScreen)) {
            content(loadedValue.value)
        }

        if (state is UIState.Failed) {
            failed(state)
        }

        if (loadedValue == null && (state is UIState.Loading<T> || state is UIState.Idle)) {
            loading()
        }
    }
}

/**
 * Applies [fillMaxSize] and [BoxScope.matchParentSize] modifiers.
 */
@Composable
fun <T> FullScreenContent(
    state: UIState<T>,
    modifier: Modifier = Modifier,
    loading: @Composable BoxScope.() -> Unit = { DefaultContentLoading() },
    failed: @Composable BoxScope.(UIState.Failed<T>) -> Unit = { DefaultContentFailure(it) },
    content: @Composable (T) -> Unit,
) {
    Content(
        state = state,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        loading = loading,
        failed = failed,
        content = content,
    )
}

@Composable
private fun BoxScope.DefaultContentLoading() {
    ContentLoading(Modifier.matchParentSize())
}

@Composable
private fun BoxScope.DefaultContentFailure(state: UIState.Failed<*>) {
    ContentFailure(state, Modifier.matchParentSize())
}
