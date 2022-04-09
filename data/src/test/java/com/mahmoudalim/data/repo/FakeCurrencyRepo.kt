package com.mahmoudalim.data.repo

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.datasource.remote.CurrencyRemoteDataSrc
import com.mahmoudalim.data.models.CurrencyResponse
import com.mahmoudalim.datasource.FakeCurrencyRemoteDataSrc

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */

class FakeCurrencyRepo : CurrencyRepository {

    private val historyList = mutableListOf<HistoryEntity>()
    private val remoteDataSrc = FakeCurrencyRemoteDataSrc()

    override suspend fun getRates(key: String): AppResponse<CurrencyResponse> {
        return remoteDataSrc.getRates(key)
    }

    override suspend fun fetchConversionsHistoryList(): List<HistoryEntity> {
        return historyList
    }

    override suspend fun insertConversionRecord(historyEntity: HistoryEntity) {
        historyList.add(historyEntity)
    }

}