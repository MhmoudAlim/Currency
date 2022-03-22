package com.mahmoudalim.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoudalim.core.utils.Const.HISTORY_TABLE
import java.io.Serializable

/**
 * Created by Mahmoud Alim on 21/03/2022.
 */

@Entity(tableName = HISTORY_TABLE)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var fromCurrency: String,
    var toCurrency: String,
    var amount: String,
    var result: String,
    var date: String,
    var timeInMillis: Long
) : Serializable