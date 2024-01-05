package com.pki.homebakery.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pki.homebakery.ui.components.bar.BottomAppBar
import com.pki.homebakery.ui.components.bar.TopAppBar
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.preview.ScreenPreviews
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.LocalSystemUiController
import com.pki.homebakery.ui.theme.contentColorFor
import com.pki.homebakery.ui.theme.isLight

/**
 * By default contents of [topBar], [bottomBar] and [content] are put in safe area (padded for systemBars).
 * If you want to handle padding manually, set [useSafeAreaOnly] to false.
 */
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    containerColor: Color = AppColors.background,
    contentColor: Color = contentColorFor(containerColor),
    setStatusBarColor: Boolean = true,
    statusBarColor: Color = AppColors.background,
    useDarkStatusBarIcons: Boolean = if (statusBarColor == Color.Transparent) {
        AppColors.background.isLight()
    } else {
        statusBarColor.isLight()
    },
    setNavigationBarColor: Boolean = true,
    navigationBarColor: Color = AppColors.background,
    useDarkNavigationBarIcons: Boolean = navigationBarColor.isLight(),
    useSafeAreaOnly: Boolean = true,
    content: @Composable () -> Unit,
) {
    if (setStatusBarColor || setNavigationBarColor) {
        val systemUiController = LocalSystemUiController.current
        SideEffect {
            if (setStatusBarColor) {
                systemUiController.setStatusBarColor(
                    color = statusBarColor,
                    darkIcons = useDarkStatusBarIcons,
                )
            }
            if (setNavigationBarColor) {
                systemUiController.setNavigationBarColor(
                    color = navigationBarColor,
                    darkIcons = useDarkNavigationBarIcons,
                )
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        color = containerColor,
        contentColor = contentColor,
    ) {
        Column {
            if (topBar != null) {
                if (useSafeAreaOnly) {
                    Box(
                        modifier = Modifier
                            .statusBarsPadding()
                            .zIndex(Float.MAX_VALUE),
                    ) {
                        topBar()
                    }
                } else {
                    topBar()
                }
            }

            val contentModifier = remember(topBar == null, bottomBar == null) {
                when {
                    topBar != null && bottomBar != null -> Modifier
                    topBar != null && useSafeAreaOnly -> Modifier.navigationBarsPadding()
                    bottomBar != null && useSafeAreaOnly -> Modifier.statusBarsPadding()
                    useSafeAreaOnly -> Modifier.systemBarsPadding()
                    else -> Modifier
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(contentModifier),
            ) {
                content()
            }

            if (bottomBar != null) {
                if (useSafeAreaOnly) {
                    Box(modifier = Modifier.navigationBarsPadding()) {
                        bottomBar()
                    }
                } else {
                    bottomBar()
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
private fun ScaffoldPreview() {
    PreviewView {
        Scaffold(
            topBar = { TopAppBar(title = "TopAppBar", onBackPressed = {}) },
            bottomBar = {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(onClick = {}) {
                            Text(text = "BottomAppBar Button")
                        }
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Text(text = "Scaffold content", color = AppColors.text)
                for (i in 0..100) {
                    Text(text = "Item $i", color = AppColors.text)
                }
            }
        }
    }
}
