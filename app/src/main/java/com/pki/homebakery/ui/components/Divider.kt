package com.pki.homebakery.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pki.homebakery.ui.preview.ComponentPreviews
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.theme.AppColors

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = AppColors.line,
    thickness: Dp = Dp.Hairline,
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}

@ComponentPreviews
@Composable
private fun DividerPreview() {
    PreviewView {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
