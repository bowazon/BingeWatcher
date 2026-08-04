package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@Preview(showBackground = true)
@Composable
fun ExamplePreviewScreenshotTest() {
    BingeWatcherPersonTopBadge(
        name = "Bryan Cranston",
        credits = "Actor · Director · Producer",
        born = "Mar 7, 1956",
    )
}