package com.mahmoudalim.data.repo

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.datasource.local.CurrencyLocalDataSrc
import com.mahmoudalim.data.datasource.remote.CurrencyRemoteDataSrc
import com.mahmoudalim.data.models.CurrencyResponse
import javax.inject.Inject

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */
class DefaultCurrencyRepo @Inject constructor(
    private val remoteDataSrc: CurrencyRemoteDataSrc,
    private val localDataSrc: CurrencyLocalDataSrc
) : CurrencyRepository {

    override suspend fun getRates(key: String):
            AppResponse<CurrencyResponse> {
        return remoteDataSrc.getRates(key)
    }

    override suspend fun fetchConversionsHistoryList(): List<HistoryEntity> {
        return localDataSrc.fetchConversionsHistoryList()
    }

    override suspend fun insertConversionRecord(historyEntity: HistoryEntity) {
        localDataSrc.insertConversionRecord(historyEntity)
    }
}