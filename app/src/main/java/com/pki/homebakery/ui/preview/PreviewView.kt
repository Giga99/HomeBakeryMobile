package com.pki.homebakery.ui.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.HomeBakeryTheme

@Composable
fun PreviewView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    HomeBakeryTheme {
        Surface(
            modifier = modifier,
            color = AppColors.background,
            contentColor = AppColors.text,
            content = content,
        )
    }
}
