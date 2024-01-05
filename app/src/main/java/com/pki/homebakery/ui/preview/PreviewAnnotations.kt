package com.pki.homebakery.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview("1 light")
@Preview("2 dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("3 large text", fontScale = 1.5f)
annotation class ComponentPreviews

@Preview("1 light")
@Preview("2 dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("3 large text", fontScale = 1.5f)
@Preview("4 small device", device = Devices.NEXUS_5)
@Preview("5 small device large text", fontScale = 1.5f, device = Devices.NEXUS_5)
annotation class ScreenPreviews
