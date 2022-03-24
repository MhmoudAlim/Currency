package com.mahmoudalim.data.repo

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.models.CurrencyResponse

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */

class FakeCurrencyRepo : CurrencyRepository {

    private val historyList = mutableListOf<HistoryEntity>()

    override suspend fun getRates(base: String, key: String): AppResponse<CurrencyResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchConversionsHistoryList(): List<HistoryEntity> {
        return historyList
    }

    override suspend fun insertConversionRecord(historyEntity: HistoryEntity) {
        historyList.add(historyEntity)
    }

}