package com.pki.homebakery.ui.components.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pki.homebakery.ui.preview.ComponentPreviews
import com.pki.homebakery.ui.preview.PreviewView
import com.pki.homebakery.ui.theme.AppColors

// TODO: change to final design
@Composable
fun ContentLoading(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = AppColors.darkGrey)
    }
}

@ComponentPreviews
@Composable
private fun Preview() {
    PreviewView {
        ContentLoading(Modifier.fillMaxSize())
    }
}
