package com.mahmoudalim.currency.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

@Composable
fun PopularListItemView(
    base: String?,
    item: Pair<String, Double>
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "1 $base")
        Text(text = " = ")
        Text(text = "${item.second} ${item.first}")
    }
    Spacer(modifier = Modifier.height(12.dp))
}