package com.mahmoudalim.data.usecase

import com.google.common.truth.Truth.assertThat
import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.models.CurrencyResponse
import com.mahmoudalim.data.repo.FakeCurrencyRepo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Mahmoud Alim on 02/04/2022.
 */
class GetAllRatesUseCaseTest {

    private lateinit var useCase: GetAllRatesUseCase
    private lateinit var fakeCurrencyRepo: FakeCurrencyRepo
    private var response: AppResponse<CurrencyResponse>? = null

    @Before
    fun setUp() {
        fakeCurrencyRepo = FakeCurrencyRepo()
        useCase = GetAllRatesUseCase(fakeCurrencyRepo)
        response = runBlocking {
            useCase("")
        }
    }

    @Test
    fun getAllRates_isSuccess() {
        assertThat(response).isNotNull()
    }

    @After
    fun tearDown() {
        response = null
    }
}