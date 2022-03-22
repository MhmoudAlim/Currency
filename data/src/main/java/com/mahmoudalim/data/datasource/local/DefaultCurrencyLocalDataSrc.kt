package com.mahmoudalim.data.datasource.local

import com.mahmoudalim.data.database.HistoryDao
import com.mahmoudalim.data.database.HistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

class DefaultCurrencyLocalDataSrc @Inject constructor(
    private val historyDao: HistoryDao
) : CurrencyLocalDataSrc {
    override suspend fun insertConversionRecord(historyEntity: HistoryEntity) {
        historyDao.insertConversionRecord(historyEntity)
    }

    override suspend fun fetchConversionsHistoryList(): List<HistoryEntity> {
        return historyDao.fetchAllRecords()
    }

}