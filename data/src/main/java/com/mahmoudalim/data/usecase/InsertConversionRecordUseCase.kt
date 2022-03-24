package com.mahmoudalim.data.usecase

import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.repo.CurrencyRepository
import javax.inject.Inject

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */
class InsertConversionRecordUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(historyEntity: HistoryEntity) {
        return repository.insertConversionRecord(historyEntity)
    }
}