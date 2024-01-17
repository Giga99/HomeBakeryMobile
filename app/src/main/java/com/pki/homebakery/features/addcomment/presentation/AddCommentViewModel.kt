package com.pki.homebakery.features.addcomment.presentation

import com.pki.homebakery.features.addcomment.data.CommentsService
import com.pki.homebakery.features.addcomment.presentation.AddCommentViewModel.Effect
import com.pki.homebakery.features.addcomment.presentation.AddCommentViewModel.State
import com.pki.homebakery.ui.InputFieldError
import com.pki.homebakery.ui.InputFieldState
import com.pki.homebakery.ui.viewmodel.BaseViewModel
import com.pki.homebakery.ui.viewmodel.UIState
import com.pki.homebakery.ui.viewmodel.asFailed
import org.koin.android.annotation.KoinViewModel

private const val ADD_COMMENT_JOB = "ADD_COMMENT_JOB"

@KoinViewModel
class AddCommentViewModel(
    cakeId: String,
    cakeTitle: String,
    private val commentsService: CommentsService,
) : BaseViewModel<State, Effect>(State(cakeId, cakeTitle)) {

    fun onCommentTextChange(commentText: String) {
        updateState { state.copy(comment = state.comment.withValue(commentText)) }
    }

    fun onAddCommentClick() = launchUniqueIfNotRunning(ADD_COMMENT_JOB) {
        if (!isCommentFormatValid()) {
            return@launchUniqueIfNotRunning
        }

        updateState { state.copy(addCommentStatus = UIState.Loading()) }

        val comment = state.comment.value
        runCatching { commentsService.addComment(state.cakeId, comment) }
            .onSuccess { effect(Effect.FinishAdding) }
            .onFailure { updateState { state.copy(addCommentStatus = it.asFailed()) } }
    }

    private fun isCommentFormatValid() =
        if (state.comment.value.isNotBlank()) {
            true
        } else {
            updateState { state.copy(comment = state.comment.withError(InputFieldError.InvalidCommentFormat)) }
            false
        }

    data class State(
        val cakeId: String,
        val cakeTitle: String,
        val comment: InputFieldState<String> = InputFieldState(""),
        val addCommentStatus: UIState<Unit> = UIState.Loaded(Unit)
    ) {
        val inputError = if (comment.isInvalid) comment.error?.message else null
        val isAddCommentButtonEnabled = comment.value.isNotBlank()
    }

    sealed class Effect {
        data object FinishAdding : Effect()
    }
}
