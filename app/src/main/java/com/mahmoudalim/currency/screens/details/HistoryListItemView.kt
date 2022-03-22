package com.mahmoudalim.currency.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mahmoudalim.data.database.HistoryEntity

/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

@Composable
 fun HistoryListItemView(item: HistoryEntity, day: String) {
    if (item.date == day)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${item.amount} ${item.fromCurrency}")
            Text(text = " : ")
            Text(text = "${item.result} ${item.toCurrency}")
        }
}