package com.mahmoudalim.data.usecase

import com.google.common.truth.Truth.assertThat
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.repo.FakeCurrencyRepo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class InsertConversionRecordUseCaseTest {

    private lateinit var useCase: InsertConversionRecordUseCase
    private lateinit var fakeCurrencyRepo: FakeCurrencyRepo

    @Before
    fun setUp() {
        fakeCurrencyRepo = FakeCurrencyRepo()
        useCase = InsertConversionRecordUseCase(fakeCurrencyRepo)
    }

    @Test
    fun insertConversionRecord_isSuccess() {
        runBlocking {
            (1..10).forEach { i ->
                useCase.invoke(
                    HistoryEntity(
                        id = i,
                        fromCurrency = i.toString(),
                        toCurrency = i.toString(),
                        amount = i.toString(),
                        result = i.toString(),
                        date = i.toString(),
                    )
                )
                assertThat(fakeCurrencyRepo.fetchConversionsHistoryList().size).isEqualTo(i)
            }
        }
    }

    @After
    fun tearDown() {
    }
}