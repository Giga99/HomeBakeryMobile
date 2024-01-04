package com.pki.homebakery.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
inline fun <S> BaseViewModel<S, *>.collectAsState(
    context: CoroutineContext = EmptyCoroutineContext,
): State<S> = stateFlow.collectAsState(context = context)

@Composable
inline fun <E> BaseViewModel<*, E>.OnEffect(crossinline block: suspend (E) -> Unit) {
    LaunchedEffect(this) { effectsFlow.collect { block(it) } }
}

@Composable
inline fun <S, E> BaseViewModel<S, E>.collectAsStateAndEffects(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline block: suspend (E) -> Unit,
): State<S> {
    OnEffect(block)
    return collectAsState(context)
}
