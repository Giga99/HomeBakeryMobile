package com.pki.homebakery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle

@Composable
fun LifecycleObserver(
    onInitialized: () -> Unit = {},
    onCreated: () -> Unit = {},
    onStarted: () -> Unit = {},
    onResumed: () -> Unit = {},
    onDestroyed: () -> Unit = {},
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(lifecycle) {
        lifecycle.currentStateFlow.collect {
            when (it) {
                Lifecycle.State.INITIALIZED -> onInitialized()
                Lifecycle.State.CREATED -> onCreated()
                Lifecycle.State.STARTED -> onStarted()
                Lifecycle.State.RESUMED -> onResumed()
                Lifecycle.State.DESTROYED -> onDestroyed()
            }
        }
    }
}
