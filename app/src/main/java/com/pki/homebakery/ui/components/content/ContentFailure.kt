package com.pki.homebakery.ui.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pki.homebakery.ui.preview.ComponentPreviews
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography
import com.pki.homebakery.ui.viewmodel.FailureDisplayType
import com.pki.homebakery.ui.viewmodel.UIState

// TODO: change to final design
@Composable
fun ContentFailure(
    error: Throwable,
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
    displayType: FailureDisplayType = ContentFailureDefaults.displayType,
) {
    val title = title ?: error.message ?: "UNKNOWN ERROR"
    val message = message ?: "Generic error message"

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = AppTypography.body,
            color = AppColors.action,
        )
        Text(
            text = message,
            style = AppTypography.body,
            color = AppColors.action,
        )
    }
}

@Composable
fun ContentFailure(
    errorState: UIState.Failed<*>,
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String? = null,
) {
    ContentFailure(
        error = errorState.error,
        modifier = modifier,
        title = title,
        message = message,
        displayType = errorState.displayType ?: ContentFailureDefaults.displayType,
    )
}

object ContentFailureDefaults {
    val displayType = FailureDisplayType.FullScreen
}

@ComponentPreviews
@Composable
private fun Preview() {
    PreviewView {
        ContentFailure(Exception("ERROR"))
    }
}
