package com.mahmoudalim.data.usecase


import com.google.common.truth.Truth.assertThat
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.repo.FakeCurrencyRepo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchConversionsHistoryListUseCaseTest {

    private lateinit var useCase: FetchConversionsHistoryListUseCase
    private lateinit var fakeCurrencyRepo: FakeCurrencyRepo

    @Before
    fun setUp() {
        fakeCurrencyRepo = FakeCurrencyRepo()
        useCase = FetchConversionsHistoryListUseCase(fakeCurrencyRepo)
        val conversionsRecords = mutableListOf<HistoryEntity>()
        ('a'..'z').forEachIndexed { index, c ->
            conversionsRecords.add(
                HistoryEntity(
                    id = index,
                    fromCurrency = c.toString(),
                    toCurrency = c.toString(),
                    amount = index.toString(),
                    result = index.toString(),
                    date = c.toString()
                )
            )
        }
        runBlocking {
            conversionsRecords.forEach {
                fakeCurrencyRepo.insertConversionRecord(it)
            }
        }

    }

    @Test
    fun fetchConversionHistoryList_isSuccess() {
        runBlocking {
            val recordsList = useCase.invoke()
            for (i in 0 until recordsList.indices.last) {
                assertThat(recordsList[i].id).isLessThan(recordsList[i + 1].id)
            }
        }
    }

    @After
    fun tearDown() {
    }
}