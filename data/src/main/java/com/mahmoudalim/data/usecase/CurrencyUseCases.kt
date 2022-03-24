package com.mahmoudalim.data.usecase

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */

data class CurrencyUseCases(
    val fetchConversionsHistoryUseCase: FetchConversionsHistoryListUseCase,
    val insertConversionRecordUseCase: InsertConversionRecordUseCase,
    val getAllRatesUseCase: GetAllRatesUseCase
)