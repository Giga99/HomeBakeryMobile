package com.pki.homebakery.ui.components.bar

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pki.homebakery.R
import com.pki.homebakery.ui.components.IconButton
import com.pki.homebakery.ui.components.Surface
import com.pki.homebakery.ui.preview.ComponentPreviews
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.theme.AppColors
import com.pki.homebakery.ui.theme.AppTypography

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    elevated: Boolean = false,
    color: Color = TopAppBarDefaults.color,
    contentColor: Color = TopAppBarDefaults.contentColor,
    content: @Composable BoxScope.() -> Unit,
) {
    val elevation by animateDpAsState(
        targetValue = if (elevated) TopAppBarDefaults.elevation else 0.dp,
        label = "elevation",
    )

    Surface(
        modifier = modifier,
        color = color,
        contentColor = contentColor,
        elevation = elevation,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = TopAppBarDefaults.minHeight),
        ) {
            content()
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    topAppBarActions: @Composable (RowScope.() -> Unit)? = null,
    elevated: Boolean = false,
    color: Color = TopAppBarDefaults.color,
    contentColor: Color = TopAppBarDefaults.contentColor,
) {
    TopAppBar(
        modifier = modifier,
        elevated = elevated,
        color = color,
        contentColor = contentColor,
        content = {
            if (navigationIcon != null) {
                Box(modifier = Modifier.align(Alignment.TopStart)) { navigationIcon() }
            }

            if (title != null || subtitle != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (title != null) title()
                    if (subtitle != null) subtitle()
                }
            }

            if (topAppBarActions != null) {
                Row(modifier = Modifier.align(Alignment.CenterEnd), content = topAppBarActions)
            }
        },
    )
}

@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBackPressed: (() -> Unit)? = null,
    topAppBarActions: @Composable (RowScope.() -> Unit)? = null,
    elevated: Boolean = false,
    color: Color = TopAppBarDefaults.color,
    contentColor: Color = TopAppBarDefaults.contentColor,
) {
    TopAppBar(
        title = { TopAppBarTitle(text = title) },
        modifier = modifier,
        subtitle = subtitle?.let { { TopAppBarSubtitle(text = it) } },
        navigationIcon = onBackPressed?.let {
            { TopAppBarBackButton(onClick = it, tint = AppColors.darkGrey) }
        },
        topAppBarActions = topAppBarActions,
        elevated = elevated,
        color = color,
        contentColor = contentColor,
    )
}

@Composable
fun TopAppBarTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text = text.uppercase(), modifier = modifier, style = AppTypography.h3)
}

@Composable
fun TopAppBarSubtitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text = text, modifier = modifier, style = AppTypography.note)
}

@Composable
fun TopAppBarButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(TopAppBarDefaults.minHeight),
        content = content,
    )
}

@Composable
fun TopAppBarButton(
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    TopAppBarButton(onClick = onClick, modifier = modifier) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = tint,
        )
    }
}

@Composable
fun TopAppBarBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    TopAppBarButton(
        onClick = onClick,
        iconResId = R.drawable.ic_back,
        modifier = modifier,
        tint = tint,
    )
}

@Composable
fun rememberElevateTopBar(state: ScrollState) = remember { derivedStateOf { state.value > 0 } }

@Composable
fun rememberElevateTopBar(state: LazyListState) = remember {
    derivedStateOf { state.firstVisibleItemIndex > 0 || state.firstVisibleItemScrollOffset > 0 }
}

object TopAppBarDefaults {
    val minHeight = 50.dp
    val elevation = 8.dp

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = AppColors.background

    val contentColor: Color
        @Composable
        @ReadOnlyComposable
        get() = AppColors.text
}

@ComponentPreviews
@Composable
private fun TopAppBarPreview() {
    PreviewView {
        TopAppBar(
            title = "TopAppBar",
            onBackPressed = {},
            topAppBarActions = {
                TopAppBarButton(
                    onClick = {},
                    iconResId = R.drawable.ic_back,
                )
            },
        )
    }
}
