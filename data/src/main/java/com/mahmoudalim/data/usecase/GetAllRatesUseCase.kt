package com.mahmoudalim.data.usecase

import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.data.models.CurrencyResponse
import com.mahmoudalim.data.repo.CurrencyRepository
import javax.inject.Inject

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */
class GetAllRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(key: String):
            AppResponse<CurrencyResponse> {
        return repository.getRates(key)
    }
}