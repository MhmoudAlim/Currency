package com.mahmoudalim.currency.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Mahmoud Alim on 27/03/2022.
 */

@Composable
fun rememberWindowInfo(): WindowInfo {
    val config = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = if (config.screenWidthDp < 600) WindowInfo.WindowType.Compact
        else WindowInfo.WindowType.Expanded,
        screenHeightInfo = if (config.screenWidthDp < 480) WindowInfo.WindowType.Compact
        else WindowInfo.WindowType.Expanded,
        screenWidth = config.screenWidthDp.dp,
        screenHeight = config.screenHeightDp.dp
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class WindowType {
        object Compact : WindowType()
        object Expanded : WindowType()
    }
}