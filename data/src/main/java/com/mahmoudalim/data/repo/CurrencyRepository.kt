package com.mahmoudalim.data.repo

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.models.CurrencyResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

interface CurrencyRepository {

    suspend fun getRates(base: String, key : String) : AppResponse<CurrencyResponse>
    suspend fun fetchConversionsHistoryList(): Flow<List<HistoryEntity>>
    suspend fun insertConversionRecord(historyEntity: HistoryEntity)
}