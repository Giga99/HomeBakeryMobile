package com.pki.homebakery.ui.viewmodel

sealed class UIState<out T> {
    abstract val loadedValue: LoadedValue<T>?

    val valueOrNull get() = loadedValue?.value
    val errorOrNull get() = (this as? Failed)?.error
    val isIdle get() = this is Idle
    val isLoading get() = this is Loading
    val isFailed get() = this is Failed
    val isLoaded get() = this is Loaded
    val shouldShowRefreshIndicator get() = (this as? Loading)?.showRefreshIndicator == true
    val shouldShowContentLoading get() = isLoading && loadedValue == null
    val hasValue get() = loadedValue != null

    data object Idle : UIState<Nothing>() {
        override val loadedValue: LoadedValue<Nothing>? = null
    }

    data class Loading<T>(
        override val loadedValue: LoadedValue<T>? = null,
        val showRefreshIndicator: Boolean = loadedValue != null,
    ) : UIState<T>()

    data class Failed<T>(
        val error: Throwable,
        override val loadedValue: LoadedValue<T>? = null,
        val displayType: FailureDisplayType? = if (loadedValue == null) {
            FailureDisplayType.FullScreen
        } else {
            FailureDisplayType.Modal
        },
    ) : UIState<T>()

    data class Loaded<T>(
        override val loadedValue: LoadedValue<T>
    ) : UIState<T>() {
        val value = loadedValue.value

        constructor(value: T) : this(LoadedValue(value))
    }

    inline fun <reified R> merge(
        vararg states: UIState<*>,
        map: (List<*>) -> R,
    ): UIState<R> {
        val result = map(states.map { it.valueOrNull })

        val loadedValue = when {
            result != null -> LoadedValue(result)
            null is R -> LoadedValue(null as R)
            else -> null
        }

        return when {
            states.any { it.isLoading } -> {
                val showRefreshIndicator = states.any { it.shouldShowRefreshIndicator }
                Loading(loadedValue, showRefreshIndicator)
            }

            states.any { it.isFailed } -> {
                val errorState = states.first { it.isFailed } as Failed<*>
                Failed(errorState.error, loadedValue, errorState.displayType)
            }

            states.all { it.isLoaded } && loadedValue != null -> {
                Loaded(loadedValue)
            }

            else -> {
                Idle
            }
        }
    }
}

/**
 * Wrapper class to represent previously loaded value in Loading and Failed states.
 * This is needed, because actual loaded value can be null. For this reason we need a wrapper to indicate
 * if we actually have previously loaded value or not.
 */
data class LoadedValue<out T>(
    val value: T
)

// TODO check this once we have a full design
enum class FailureDisplayType { FullScreen, Modal }

fun <T> UIState<T>.asLoading(showRefreshIndicator: Boolean = true) = UIState.Loading(
    loadedValue = loadedValue,
    showRefreshIndicator = if (isFailed) hasValue else showRefreshIndicator,
)

fun <T> UIState<T>.asFailed(
    error: Throwable,
    loadedValue: LoadedValue<T>? = this.loadedValue,
    displayType: FailureDisplayType? = if (hasValue) {
        FailureDisplayType.Modal
    } else {
        FailureDisplayType.FullScreen
    },
) = UIState.Failed(
    error = error,
    loadedValue = loadedValue,
    displayType = displayType,
)

fun <T> Throwable.asFailed(
    displayType: FailureDisplayType? = null,
) = UIState.Failed<T>(this, displayType = displayType)

fun <T> T.asLoaded() = UIState.Loaded(this)
