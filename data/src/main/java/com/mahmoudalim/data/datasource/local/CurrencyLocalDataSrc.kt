package com.mahmoudalim.data.datasource.local

import com.mahmoudalim.data.database.HistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
interface CurrencyLocalDataSrc {

    suspend fun insertConversionRecord(historyEntity: HistoryEntity)
    suspend fun fetchConversionsHistoryList(): Flow<List<HistoryEntity>>
}