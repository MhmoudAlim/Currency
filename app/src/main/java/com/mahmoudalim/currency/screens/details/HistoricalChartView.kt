package com.mahmoudalim.currency.screens.details

import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Mahmoud Alim on 26/03/2022.
 */


@Composable
fun HistoricalChartView(days: MutableList<Pair<String, Int>>) {
    var start by remember { mutableStateOf(false) }
    val preHeight by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = FloatTweenSpec(duration = 2000)
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(8.dp)
    ) {
        start = true
        days.forEach {
            val fraction =
                if ((it.second) != 0) (it.second.toFloat() * preHeight).div(100) else 0.01f
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .fillMaxWidth(fraction)
                        .background(
                            Color.Red,
                            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                        ),
                )
                if (it.second == 0)
                    Text(text = "No Data for:")
                Text(text = it.first)
            }
        }
    }
}

