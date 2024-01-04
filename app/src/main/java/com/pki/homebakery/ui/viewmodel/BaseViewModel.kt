package com.pki.homebakery.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<State, Effect>(
    initialState: State
) : ViewModel() {
    private val stateChangeInputsFlow =
        MutableSharedFlow<StateChange<State>>(extraBufferCapacity = Int.MAX_VALUE)
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()
    val state = stateFlow.value

    private val _effectsFlow = MutableSharedFlow<Effect>(extraBufferCapacity = Int.MAX_VALUE)
    val effectsFlow: SharedFlow<Effect> = _effectsFlow.asSharedFlow()

    private val uniqueJobs by lazy { hashMapOf<String, Job>() }

    init {
        launchInViewModel {
            stateChangeInputsFlow
                .flatMapConcat { flow(it.stateChanges) }
                .collect { state -> _stateFlow.update { state } }
        }
    }

    protected fun updateState(stateChange: suspend () -> State) {
        stateChangeInputsFlow.tryEmit(StateChange { emit(stateChange()) })
    }

    protected fun effect(effect: Effect) {
        if (_effectsFlow.subscriptionCount.value > 0) {
            _effectsFlow.tryEmit(effect)
        } else {
            launchInViewModel {
                _effectsFlow.subscriptionCount
                    .filter { it > 0 }
                    .take(1)
                    .collect { _effectsFlow.tryEmit(effect) }
            }
        }
    }

    /**
     * Launches a new coroutine using [viewModelScope]. This is mostly used in `init` block.
     * If you use it in some other place, check maybe [launchUnique] or [launchUniqueIfNotRunning] is more appropriate.
     *
     * Example: If you want to fetch some data when ViewModel is created, you can do it like this.
     */
    protected fun launchInViewModel(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job = viewModelScope.launch(context, start, block)

    /**
     * Cancels current job with the same [uniqueJobId] if one is already running then launches new coroutine using [launchInViewModel].
     *
     * Example: If refresh is called multiple times, but previous refresh is still running, then this method will cancel previous refresh and start new one.
     */
    protected fun launchUnique(
        uniqueJobId: String,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        cancelUniqueJob(uniqueJobId)

        val job = launchInViewModel { block() }
        job.invokeOnCompletion {
            val jobForUniqueId = uniqueJobs[uniqueJobId]
            if (job == jobForUniqueId) {
                uniqueJobs.remove(uniqueJobId)
            }
        }

        uniqueJobs[uniqueJobId] = job
        return job
    }

    /**
     * Does nothing if job with the same [uniqueJobId] is already running. Otherwise does the same as [launchUnique].
     *
     * Example: If refresh is called multiple times, but previous refresh is still running, then this method will do nothing.
     */
    protected fun launchUniqueIfNotRunning(
        uniqueJobId: String,
        block: suspend CoroutineScope.() -> Unit
    ): Job? {
        val currentJob = uniqueJobs[uniqueJobId]
        return if (currentJob?.isActive == true) {
            null
        } else {
            launchUnique(uniqueJobId, block)
        }
    }

    protected fun cancelUniqueJob(uniqueJobId: String) {
        uniqueJobs[uniqueJobId]?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        uniqueJobs.clear()
    }

    private data class StateChange<State>(
        val stateChanges: suspend FlowCollector<State>.() -> Unit
    )
}
