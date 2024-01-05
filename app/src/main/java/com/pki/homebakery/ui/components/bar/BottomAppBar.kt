package com.pki.homebakery.ui.components.bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import com.pki.homebakery.ui.preview.ComponentPreviews
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.theme.AppColors

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(start = 16.dp, end = 16.dp, bottom = 10.dp),
    content: @Composable () -> Unit,
) {
    val backgroundColor = AppColors.background
    Box(
        modifier = modifier
            .drawWithCache {
                onDrawBehind {
                    val fadeHeight = BottomAppBarDefaults.FadeHeight.toPx()
                    drawRect(
                        brush = Brush.verticalGradient(
                            0f to backgroundColor.copy(0f),
                            fadeHeight / (size.height + fadeHeight) to backgroundColor,
                            1f to backgroundColor,
                            startY = -fadeHeight,
                            tileMode = TileMode.Repeated,
                        ),
                        topLeft = Offset(0f, -fadeHeight),
                        size = size.copy(height = size.height + fadeHeight),
                    )
                }
            }
            .padding(contentPadding),
    ) {
        CompositionLocalProvider(LocalContentColor provides AppColors.text) {
            content()
        }
    }
}

object BottomAppBarDefaults {
    val FadeHeight = 30.dp
}

@ComponentPreviews
@Composable
private fun BottomAppBarPreview() {
    PreviewView {
        BottomAppBar {
            Text(text = "BottomAppBar content")
        }
    }
}
